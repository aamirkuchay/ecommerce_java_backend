package com.ecommerce;


import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Ecommerce",
				description = "Ecommerce Application",
				version = "v1",
				contact = @Contact(name = "MR.Ecommerce", email = "K@gmail.com",url = "https://ecommerce.com"),
				license =@License(
						name = "Apache 2.O",
						url = "https://www.ht.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Ecommerce Api Documents",
				url = "https://ecommerce.com"
		)
)
@EnableCaching
public class EcommerceApplication  implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Optional<Role> adminRoleOpt = roleRepository.findByName("ROLE_ADMIN");
		if (adminRoleOpt.isPresent()) {
			Role adminRole = adminRoleOpt.get();
			Optional<User> adminAccountOpt = userRepository.findByRole(adminRole);
			if (adminAccountOpt.isEmpty()) {
				User user = new User();
				user.setName("KLCC");
				user.setPhoneNumber("1234567543");
				user.setAddress("Srinager");
				user.setUsername("admin");
				user.setPassword(new BCryptPasswordEncoder().encode("admin"));
				user.setRole(adminRole);
				userRepository.save(user);
			}
		} else {
			throw new RuntimeException("ROLE_ADMIN not found in the database.");
		}
	}

	}
