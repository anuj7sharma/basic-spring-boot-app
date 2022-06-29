package com.learning.basic.basicspriongboot.controller;

import com.learning.basic.basicspriongboot.entity.UserEntity;
import com.learning.basic.basicspriongboot.exception.BadRequestException;
import com.learning.basic.basicspriongboot.model.RegisterRequest;
import com.learning.basic.basicspriongboot.repository.UserRepository;
import com.learning.basic.basicspriongboot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;
    private UserController userController;


    @BeforeEach
    void setup() {
        UserService userService = new UserService(userRepository);
        userController = new UserController(userService);
    }

    @Test
    void register_GivenValidInfo_Return201Response() {
        // Arrange
        String firstName = "Anuj";
        String lastName = "Sharma";
        String email = "anuj@gmail.com";
        RegisterRequest request = new RegisterRequest(firstName, lastName, email);
        UserEntity userEntity = new UserEntity(request.getFirstName(), request.getLastName(), request.getEmail());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        // Act
        ResponseEntity responseEntity = userController.register(request);

        // Assert
        assertAll("Registration should be successful",
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()));
    }

    @Test
    void register_GivenEmailAlreadyExists_ReturnBadRequest() {
        // Arrange
        String firstName = "Anuj";
        String lastName = "Sharma";
        String email = "anuj@gmail.com";
        RegisterRequest request = new RegisterRequest(firstName, lastName, email);
        UserEntity userEntity = new UserEntity(request.getFirstName(), request.getLastName(), request.getEmail());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

        // Act, Assert
        assertThrows(BadRequestException.class, () -> userController.register(request));
    }

    @Test
    @Disabled
    void register_GivenBlankFirstName_ReturnBadRequest() {
        // Arrange
        String firstName = "";
        String lastName = "Sharma";
        String email = "anuj@gmail.com";
        RegisterRequest request = new RegisterRequest(firstName, lastName, email);
        UserEntity userEntity = new UserEntity(request.getFirstName(), request.getLastName(), request.getEmail());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        // Act, Assert
        assertThrows(Exception.class, () -> userController.register(request));
    }

    @Test
    @Disabled
    void register_GivenInvalidEmailFormat_ReturnBadRequest() {
        // Arrange
        String firstName = "Anuj";
        String lastName = "Sharma";
        String email = "abc.com";
        RegisterRequest request = new RegisterRequest(firstName, lastName, email);
        UserEntity userEntity = new UserEntity(request.getFirstName(), request.getLastName(), request.getEmail());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        // Act, Assert
        assertThrows(Exception.class, () -> userController.register(request));
    }
}
