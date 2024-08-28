package com.sisd.sisd.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject,String text);
}
