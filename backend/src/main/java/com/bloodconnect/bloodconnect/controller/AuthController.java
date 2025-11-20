package com.bloodconnect.bloodconnect.controller;

import com.bloodconnect.bloodconnect.model.User;
import com.bloodconnect.bloodconnect.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        User savedUser = authService.register(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email,@RequestParam String password){
        User user = authService.login(email,password);
        return ResponseEntity.ok(user);
    }

} */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        // ensure new fields don't remain null
        if (user.getIsDonor() == null) user.setIsDonor(false);
        if (user.getAvailable() == null) user.setAvailable(true);

        User savedUser = authService.register(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email,@RequestParam String password){
        User user = authService.login(email,password);
        return ResponseEntity.ok(user);
    }
}



