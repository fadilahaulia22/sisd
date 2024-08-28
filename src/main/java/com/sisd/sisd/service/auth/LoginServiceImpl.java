package com.sisd.sisd.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sisd.sisd.dto.auth.LoginRequestDto;
import com.sisd.sisd.dto.auth.LoginResponseDto;
import com.sisd.sisd.entity.Users;
import com.sisd.sisd.repository.UsersRepository;
import com.sisd.sisd.util.JwtUtil;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    JwtUtil jwtUtil;
    
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        Users user = usersRepository
        .findByUserName(loginRequestDto.getUsername())
        .orElse(null);

        if (user != null) {
            boolean isMatch = passwordEncoder.matches(loginRequestDto.getPassword(), 
            user.getPassword());

            if (isMatch) {
                LoginResponseDto loginResponseDto = new LoginResponseDto();
                loginResponseDto.setUsername(user.getUserName());
                loginResponseDto.setRole(user.getRoles().getRoleName());
                loginResponseDto.setToken(jwtUtil.generateToken(user));
                return loginResponseDto;
            }
        }
        // throw new BadCredentialsException("Invalid username or password");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Invalid username or password");
    }
}
