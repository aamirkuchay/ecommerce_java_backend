package com.ecommerce.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter @Setter @AllArgsConstructor
@Entity
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "Please Enter Name")
	@NotEmpty(message = "Please Enter Name")
	@Column(name = "name")
	private String name;

	@NotEmpty(message = "Please Enter Password")
	@Column(name = "password")
	private String password;

	@NotNull(message = "Please Enter Phone Number")
	@NotEmpty(message = "PhoneNumber is required")
	@Size(min = 10, max = 12, message = "Phone Number should be atleast 10-12 digits")
	@Digits(fraction = 0, integer = 12, message = "Only digits are allowed")
	@Column(name = "phone_number")
	private String phoneNumber;

	/* @NotEmpty(message = "Please Enter Address") */
	@Column(name = "address")
	private String address;

	@ManyToOne
	@JoinColumn(name = "role_id")
	@NotNull(message = "Please Select Role")
	@JsonBackReference
	private Role role;

	@NotNull
	@NotEmpty(message = "Please Enter Username")
	@Column(name = "username")
	private String username;

	@OneToOne(mappedBy = "user")
	private Cart cart;

	@UpdateTimestamp
	private LocalDateTime updated;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)

	private List<Address> addresses;

	@OneToMany(mappedBy = "user")
	private List<Order> orders;

	public User() {
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.getName()));
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
