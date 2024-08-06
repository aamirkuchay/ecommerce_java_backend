package com.ecommerce.repository;
import com.ecommerce.entity.Address;
import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String email);
    Optional<User> findByRole(Role role);
	boolean existsByUsername(String username);
}
