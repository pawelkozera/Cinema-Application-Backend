package isi.cinema.controller;

import isi.cinema.model.Movie;
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

    public ResponseEntity<?> checkAdminAuthorization(Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        if (!userAuthentication.checkAuthentication(authentication)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        Role role = Role.ADMIN;
        if (!userAuthentication.checkRole(authentication, role)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        return null;
    }
}
