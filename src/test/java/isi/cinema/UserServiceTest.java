package isi.cinema;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import isi.cinema.DTO.PasswordDTO;
import isi.cinema.model.User;
import isi.cinema.repository.UserRepository;
import isi.cinema.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    @Test
    public void testRegister() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        when(userRepository.save(any(User.class))).thenReturn(user);
        User registeredUser = userService.register(user);

        assertNotNull(registeredUser);
        assertEquals(user.getUsername(), registeredUser.getUsername());
        assertTrue(encoder.matches("password", registeredUser.getPassword()));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testLogin_ValidCredentials() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword(encoder.encode("password"));

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        User loginAttempt = new User();
        loginAttempt.setUsername("testuser");
        loginAttempt.setPassword("password");

        User loggedInUser = userService.login(loginAttempt);
        assertNotNull(loggedInUser);
        assertEquals(user.getUsername(), loggedInUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    public void testLogin_InvalidPassword() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword(encoder.encode("password"));

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        User loginAttempt = new User();
        loginAttempt.setUsername("testuser");
        loginAttempt.setPassword("wrongpassword");

        User loggedInUser = userService.login(loginAttempt);
        assertNull(loggedInUser);
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    public void testLogin_UserNotFound() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        User loginAttempt = new User();
        loginAttempt.setUsername("nonexistentuser");
        loginAttempt.setPassword("password");

        User loggedInUser = userService.login(loginAttempt);
        assertNull(loggedInUser);
    }

    @Test
    public void testGetEmail_UserExists() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        user.setEmail("testuser@example.com");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        String result = userService.getEmail(username);

        assertEquals(user.getEmail(), result);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testGetEmail_UserNotFound() {
        String username = "testuser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        String result = userService.getEmail(username);

        assertNull(result);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testUpdateUser_Success() {
        String userName = "testuser";
        String oldPassword = "password";
        String newPassword = "newpassword";

        User user = new User();
        user.setUsername(userName);
        user.setPassword(encoder.encode(oldPassword));

        when(userRepository.findByUsername(userName)).thenReturn(Optional.of(user));

        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setOldPassword(oldPassword);
        passwordDTO.setNewPassword(newPassword);

        String result = userService.updateUser(passwordDTO, userName);

        assertEquals("Successfully changed password", result);

        assertTrue(encoder.matches(newPassword, user.getPassword()));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser_WrongOldPassword() {
        String userName = "testuser";
        String oldPassword = "password";
        String newPassword = "newpassword";

        User user = new User();
        user.setUsername(userName);
        user.setPassword(encoder.encode(oldPassword));

        when(userRepository.findByUsername(userName)).thenReturn(Optional.of(user));

        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setOldPassword("wrongpassword");
        passwordDTO.setNewPassword(newPassword);

        String result = userService.updateUser(passwordDTO, userName);

        assertEquals("Wrong old password", result);

        assertFalse(encoder.matches(newPassword, user.getPassword()));
        verify(userRepository, never()).save(Mockito.any());
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        String userName = "nonexistentuser";

        when(userRepository.findByUsername(userName)).thenReturn(Optional.empty());

        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setOldPassword("password");
        passwordDTO.setNewPassword("newpassword");

        String result = userService.updateUser(passwordDTO, userName);

        assertNull(result);
        verify(userRepository, never()).save(Mockito.any());
    }
}