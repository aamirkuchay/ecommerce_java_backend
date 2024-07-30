package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.Optional;

import com.ecommerce.dto.*;
import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.AuthenticationService;
import com.ecommerce.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtService jwtService;

	@Autowired
	RoleRepository roleRepository;

	public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JwtService jwtService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	@Override
	public User signUp(SignupRequest signupRequest) {
		  if (userRepository.existsByUsername(signupRequest.getUsername())) {
	            throw new RuntimeException("Username already taken");
	        }
		User user = new User();
		user.setName(signupRequest.getName());
		user.setAddress(signupRequest.getAddress());
		user.setPhoneNumber(signupRequest.getPhoneNumber());
		user.setUsername(signupRequest.getUsername());
		user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		Optional<Role> roleOptional = roleRepository.findByName("ROLE_USER");
		if (roleOptional.isPresent()) {
			user.setRole(roleOptional.get());
		} else {
			throw new RuntimeException("Role not found");
		}
		return userRepository.save(user);

	}

	@Override
	public JwtAuthenticationResponse signIn(SignInRequest sign) {
		if (sign.getPassword() == null) {
			throw new IllegalArgumentException("Password is required");
		}
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(sign.getUsername(), sign.getPassword()));
		var user = userRepository.findByUsername(sign.getUsername())
				.orElseThrow(() -> new IllegalArgumentException("Invalid Email or Password"));
		var jwt = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
		jwtAuthenticationResponse.setToken(jwt);
		jwtAuthenticationResponse.setRefreshToken(refreshToken);
		jwtAuthenticationResponse.setUser(user);
		return jwtAuthenticationResponse;

	}

	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String email = jwtService.extractUsername(refreshTokenRequest.getToken());
		User user = userRepository.findByUsername(email).orElseThrow(() -> new RuntimeException("User not found"));

		if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
			String newToken = jwtService.generateToken(user);

			JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
			jwtAuthenticationResponse.setToken(newToken);
			jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
			return jwtAuthenticationResponse;
		} else {
			throw new RuntimeException("Invalid token");
		}
	}

	@Override
	public User updateUser(Long id, UpdateUserRequest updateUserRequest) {
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			throw new ResourceNotFoundException("User not found");
		}

		User user = userOptional.get();
		user.setName(updateUserRequest.getName());
		user.setAddress(updateUserRequest.getAddress());
		user.setPhoneNumber(updateUserRequest.getPhoneNumber());
		user.setUsername(updateUserRequest.getUsername());

		if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isEmpty()) {
			user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
		}

		if (updateUserRequest.getRole().getName() != null && !updateUserRequest.getRole().getName().isEmpty()) {
			Optional<Role> roleOptional = roleRepository.findByName(updateUserRequest.getRole().getName());
			if (roleOptional.isPresent()) {
				user.setRole(roleOptional.get());
			} else {
				throw new RuntimeException("Role not found");
			}
		}

		return userRepository.save(user);
	}

	@Override
	public User getUserById(Long id) {
		User singleUSerOptional = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found with given id " + id));
		return singleUSerOptional;
	}

	@Override
	public Page<User> findAllByPage(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

}
