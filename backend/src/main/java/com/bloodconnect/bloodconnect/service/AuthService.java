package com.bloodconnect.bloodconnect.service;

import com.bloodconnect.bloodconnect.model.User;

public interface AuthService {
    User register(User user);

    User login(String email, String password);
}