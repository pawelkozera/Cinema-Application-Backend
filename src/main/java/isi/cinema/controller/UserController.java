package isi.cinema.controller;

import isi.cinema.model.Role;
import isi.cinema.model.Room;
import isi.cinema.model.User;
import isi.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.register(user);

        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User loggedUser = userService.login(user);

        if (loggedUser != null) {
            return new ResponseEntity<>(loggedUser, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/checkRole")
    public ResponseEntity<?> checkRole(Authentication authentication, Role role) {
        UserAuthentication userAuthentication = new UserAuthentication();
        if (!userAuthentication.checkAuthentication(authentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        if (!userAuthentication.checkRole(authentication, role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient permissions");
        }

        return ResponseEntity.ok("Authorized access");
    }

    @RequestMapping(value="/delete/{id}", method={RequestMethod.DELETE, RequestMethod.GET})
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if(user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if(updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getEmail")
    public ResponseEntity<String> getEmail(Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        if (!userAuthentication.checkAuthentication(authentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = userService.getEmail(authentication.getName());
        
        if(email != null) {
            return ResponseEntity.ok(email);
        }
        return ResponseEntity.notFound().build();
    }
}
