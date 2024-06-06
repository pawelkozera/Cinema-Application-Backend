package isi.cinema.Authentication;

import isi.cinema.refreshToken.TokenRefreshRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/auth")

@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest registerRequest) {

        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/refreshtoken")
    public ResponseEntity<AuthenticationResponse> refreshAccessToken(@RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(tokenRefreshRequest));
    }
}



