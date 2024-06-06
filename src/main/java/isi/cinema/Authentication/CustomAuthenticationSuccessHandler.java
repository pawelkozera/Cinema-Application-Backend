package isi.cinema.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import isi.cinema.model.Role;
import isi.cinema.model.User;
import isi.cinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private AuthenticationService authenticationService;
    public CustomAuthenticationSuccessHandler() {
    }

    @Override
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Authentication authentication) throws IOException, jakarta.servlet.ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String token = authenticationService.authenticateWithGoogle(oauth2User);
        System.out.println("Token:"+token);
        String redirectUrl = "http://localhost:5173/Kielce/login?token=" + token;
        response.sendRedirect(redirectUrl);
    }
}
