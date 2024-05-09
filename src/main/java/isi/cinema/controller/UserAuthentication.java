package isi.cinema.controller;

import isi.cinema.model.Role;
import isi.cinema.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public class UserAuthentication {
    public boolean checkAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        return true;
    }

    public boolean checkRole(Authentication authentication, Role role) {
        User user = (User) authentication.getPrincipal();
        if (!user.getRole().equals(role)) {
            return false;
        }

        return true;
    }
}
