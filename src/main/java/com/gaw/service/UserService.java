package com.gaw.service;

import com.gaw.model.entity.User;
import com.gaw.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
class UserService {

    private final UserRepository userRepository;

    User getUserByName(String email) {
        if (Objects.nonNull(email)) {
            return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException(String.format("User with email %s not found.", email)));
        } else {
            throw new RuntimeException("Null email is not allowed.");
        }
    }

    boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
