package com.bloodconnect.bloodconnect.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}

