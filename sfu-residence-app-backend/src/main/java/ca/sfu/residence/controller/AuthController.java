package ca.sfu.residence.controller;

import ca.sfu.residence.model.User;
import ca.sfu.residence.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User saved = userService.register(user);
        return ResponseEntity.ok(Map.of(
            "message", "User registered successfully",
            "userId", saved.getId()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");

        Optional<User> user = userService.authenticate(email, password);
        if (user.isPresent()) {
            // TODO: generate and return JWT (next step)
            return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "userId", user.get().getId(),
                "email", user.get().getEmail()
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }
}
