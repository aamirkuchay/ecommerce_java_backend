package com.ecommerce.service;

import com.ecommerce.dto.*;
import com.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthenticationService {
	
	 User signUp(SignupRequest signupRequest);
	    JwtAuthenticationResponse signIn(SignInRequest sign);
	    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
		User updateUser(Long id, UpdateUserRequest updateUserRequest);
		User getUserById(Long id);
		Page<User> findAllByPage(Pageable pageable);

}
