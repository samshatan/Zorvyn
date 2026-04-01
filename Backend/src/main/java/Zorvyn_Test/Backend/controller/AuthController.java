package Zorvyn_Test.Backend.controller;

import Zorvyn_Test.Backend.dto.JwtResponse;
import Zorvyn_Test.Backend.dto.LoginRequest;
import Zorvyn_Test.Backend.dto.RegisterRequest;
import Zorvyn_Test.Backend.model.User;
import Zorvyn_Test.Backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.authenticateUser(loginRequest);
        // Getting user details implies we can send role/username back but for simplicity we return just token 
        // Although the JWT has the username in it already. Let's just return what we have.
        return ResponseEntity.ok(new JwtResponse(token, loginRequest.getUsername(), "N/A")); 
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        User user = authService.registerUser(signUpRequest);
        return ResponseEntity.ok(user);
    }
}
