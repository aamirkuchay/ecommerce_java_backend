package com.ecommerce.controller;

import com.ecommerce.dto.UpdateUserRequest;
import com.ecommerce.entity.User;
import com.ecommerce.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private AuthenticationService authenticationService;


	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest) {
		return ResponseEntity.ok(authenticationService.updateUser(id, updateUserRequest));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(authenticationService.getUserById(id));
	}

	@PreAuthorize("hasAnyRole('ADMIN','VERIFIER')")
	@GetMapping
	public Page<User> findAllByPage(@RequestParam(defaultValue = "1") int page) {
		Pageable pageable = PageRequest.of(page - 1, 10);
		return authenticationService.findAllByPage(pageable);
	}

}
