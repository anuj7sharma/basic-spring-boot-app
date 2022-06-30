package basic.springboot.service;

import basic.springboot.entity.UserEntity;
import basic.springboot.exception.BadRequestException;
import basic.springboot.model.RegisterRequest;
import basic.springboot.model.RegisterResponse;
import basic.springboot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setup(){
        userService = new UserService(userRepository);
    }

    @Test
    void register_GivenValidInfo_ReturnCorrectData() {
        // Arrange
        String firstName = "Anuj";
        String lastName = "Sharma";
        String email = "anuj@gmail.com";
        RegisterRequest request = new RegisterRequest(firstName, lastName, email);
        UserEntity userEntity = new UserEntity(request.getFirstName(), request.getLastName(), request.getEmail());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        // Act
        RegisterResponse response = userService.register(request);

        // Assert
        assertAll("Registration should be successful",
                () -> assertNotNull(response),
                () -> assertEquals(firstName, response.getFirstName()),
                () -> assertEquals(lastName, response.getLastName()),
                () -> assertEquals(email, response.getEmail()));
    }

    @Test
    void register_GivenEmailAlreadyExists_ThrowsException() {
        // Arrange
        String firstName = "Anuj";
        String lastName = "Sharma";
        String email = "anuj@gmail.com";
        RegisterRequest request = new RegisterRequest(firstName, lastName, email);
        UserEntity userEntity = new UserEntity(request.getFirstName(), request.getLastName(), request.getEmail());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

        // Act, Assert
        assertThrows(BadRequestException.class, ()-> userService.register(request));
    }
}
