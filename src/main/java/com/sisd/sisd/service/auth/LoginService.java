package com.sisd.sisd.service.auth;

import org.springframework.stereotype.Service;

import com.sisd.sisd.dto.auth.LoginRequestDto;
import com.sisd.sisd.dto.auth.LoginResponseDto;

@Service
public interface LoginService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
