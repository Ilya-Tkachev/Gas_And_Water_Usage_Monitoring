package com.gaw.repository;

import com.gaw.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@NotNull @Email String email);

    boolean existsByEmail(@NotNull @Email String email);
}
