package com.ecommerce.controller;

import com.ecommerce.dto.JwtAuthenticationResponse;
import com.ecommerce.dto.RefreshTokenRequest;
import com.ecommerce.dto.SignInRequest;
import com.ecommerce.dto.SignupRequest;
import com.ecommerce.entity.User;
import com.ecommerce.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	private final AuthenticationService authenticationService;

	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/signup")
	public ResponseEntity<User> signUp(@RequestBody SignupRequest signupRequest) {
		return ResponseEntity.ok(authenticationService.signUp(signupRequest));
	}

	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest) {
		return ResponseEntity.ok(authenticationService.signIn(signInRequest));
	}

	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
	}
}
