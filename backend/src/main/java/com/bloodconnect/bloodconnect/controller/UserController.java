package com.bloodconnect.bloodconnect.controller;

import com.bloodconnect.bloodconnect.model.User;
import com.bloodconnect.bloodconnect.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get users by blood group
    @GetMapping("/blood-group/{bloodGroup}")
    public ResponseEntity<List<User>> getUsersByBloodGroup(@PathVariable String bloodGroup) {
        return ResponseEntity.ok(userService.getUsersByBloodGroup(bloodGroup));
    }

    // Optional: get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
}
