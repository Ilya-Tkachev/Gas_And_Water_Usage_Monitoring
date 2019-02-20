package com.gaw.service;

import com.gaw.model.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void getUserByNameTest1() {
        //Given
        String email = "notExistingUser";

        //When
        //Then
        assertThrows(RuntimeException.class, () -> userService.getUserByName(email));
    }

    @Test
    void getUserByNameTest2() {
        //Given
        String email = "email@gmail.com";

        //When
        User user = userService.getUserByName(email);

        //Then
        assertEquals(email, user.getEmail());
    }

    @Test
    void userExists1() {
        //Given
        String email = "notExistingUser";

        //When
        //Then
        assertFalse(userService.userExists(email));
    }

    @Test
    void userExists2() {
        //Given
        String email = "email@gmail.com";

        //When
        //Then
        assertTrue(userService.userExists(email));
    }
}