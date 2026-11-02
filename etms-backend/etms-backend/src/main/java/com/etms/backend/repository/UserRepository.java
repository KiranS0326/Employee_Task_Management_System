package com.etms.backend.repository;

import com.etms.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE TRIM(LOWER(u.username)) = TRIM(LOWER(:username))")
    Optional<User> findByUsername(@Param("username") String username);
}