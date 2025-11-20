package com.bloodconnect.bloodconnect.service;

import com.bloodconnect.bloodconnect.model.User;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUserByEmail(String email);
    List<User> getUsersByBloodGroup(String bloodGroup);
    List<User> getAllUsers();

    // 🔥 NEW: required for distance-based donor mapping
    User getUserById(Long id);
}
