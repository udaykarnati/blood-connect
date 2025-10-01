package com.bloodconnect.bloodconnect.serviceImpl;

import com.bloodconnect.bloodconnect.model.User;
import com.bloodconnect.bloodconnect.repository.UserRepository;
import com.bloodconnect.bloodconnect.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public User register(User user){
        user.setPassword((passwordEncoder.encode(user.getPassword())));
        return userRepository.save(user);

    }

    @Override
    public User login(String email,String password){
        return userRepository.findByEmail(email)
                .filter(u->passwordEncoder.matches(password,u.getPassword()))
                .orElseThrow(()-> new RuntimeException("Invalid email or password"));
    }

}
