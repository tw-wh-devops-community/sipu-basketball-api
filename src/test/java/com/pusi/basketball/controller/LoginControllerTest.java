package com.pusi.basketball.controller;

import com.pusi.basketball.controller.request.UserDto;
import com.pusi.basketball.model.User;
import com.pusi.basketball.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @InjectMocks
    private LoginController controller;

    @Mock
    private UserRepository userRepository;

    @Test
    public void should_response_ok_when_username_password_match() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");

        User user = new User();
        user.setUsername("testUser");
        user.setPassword(new BCryptPasswordEncoder().encode("testPassword"));
        doReturn(Optional.of(user)).when(userRepository).findUserByUsername("testUser");

        ResponseEntity<String> responseEntity = controller.login(userDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Login Success!", responseEntity.getBody());
    }

    @Test
    public void should_response_forbidden_when_user_not_exist() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");
        doReturn(Optional.empty()).when(userRepository).findUserByUsername("testUser");

        ResponseEntity<String> responseEntity = controller.login(userDto);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("Invalid username or password", responseEntity.getBody());
    }

    @Test
    public void should_response_forbidden_when_password_not_correct() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");

        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        doReturn(Optional.of(user)).when(userRepository).findUserByUsername("testUser");

        ResponseEntity<String> responseEntity = controller.login(userDto);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("Invalid username or password", responseEntity.getBody());
    }
}
