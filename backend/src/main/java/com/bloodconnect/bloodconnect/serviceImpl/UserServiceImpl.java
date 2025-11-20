package com.bloodconnect.bloodconnect.serviceImpl;

import com.bloodconnect.bloodconnect.model.User;
import com.bloodconnect.bloodconnect.repository.UserRepository;
import com.bloodconnect.bloodconnect.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 🔹 Save user (used in registration)
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // 🔹 Get user by email (used for login & request creator lookup)
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // 🔹 Get users by blood group
    @Override
    public List<User> getUsersByBloodGroup(String bloodGroup) {
        return userRepository.findByBloodGroup(bloodGroup);
    }

    // 🔹 Get all users
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 🔥 NEW: Get user by ID (needed for distance-based donor matching)
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
