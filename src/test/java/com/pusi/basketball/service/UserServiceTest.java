package com.pusi.basketball.service;

import com.pusi.basketball.model.User;
import com.pusi.basketball.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void should_find_user_by_username_when_user_exist() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        doReturn(Optional.of(user)).when(userRepository).findUserByUsername("testUser");

        Optional<User> result = userService.findUserByUsername("testUser");

        assertTrue(result.isPresent());
        assertEquals(user.getUsername(), result.get().getUsername());
        assertEquals(user.getPassword(), result.get().getPassword());
    }

    @Test
    void should_not_find_user_by_username_when_user_not_exist() {
        doReturn(Optional.empty()).when(userRepository).findUserByUsername("testUser");

        Optional<User> result = userService.findUserByUsername("testUser");

        assertFalse(result.isPresent());
    }
}
