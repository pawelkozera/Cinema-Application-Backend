package isi.cinema.service;

import isi.cinema.DTO.PasswordDTO;
import isi.cinema.model.User;
import isi.cinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    public User login(User user) {
        Optional<User> userExist = userRepository.findByUsername(user.getUsername());

        if (userExist.isPresent()) {
            User existingUser = userExist.get();
            if (encoder.matches(user.getPassword(), existingUser.getPassword())) {
                return existingUser;
            }
        }

        return null;
    }

    public String updateUser(PasswordDTO passwordDTO, String userName) {
        Optional<User> userOptional = userRepository.findByUsername(userName);

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            if (encoder.matches(passwordDTO.getOldPassword(), user.getPassword())) {
                String encodedPassword = encoder.encode(passwordDTO.getNewPassword());
                user.setPassword(encodedPassword);
                userRepository.save(user);

                return "Successfully changed password";
            }
            else {
                return "Wrong old password";
            }
        }
        return  null;
    }

    public String getEmail(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(User::getEmail).orElse(null);
    }
}
