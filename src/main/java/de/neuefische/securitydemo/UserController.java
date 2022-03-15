package de.neuefische.securitydemo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public UserDocument createUser(@RequestBody UserDocument user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.createUser(user);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDocument> me(Principal principal) {
        return ResponseEntity.of(userService.findByEmail(principal.getName()));
    }

}
