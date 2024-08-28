package com.sisd.sisd.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sisd.sisd.dto.GenericResponse;
import com.sisd.sisd.dto.auth.LoginRequestDto;
import com.sisd.sisd.dto.auth.LoginResponseDto;
import com.sisd.sisd.service.auth.LoginService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class LoginController {
    
    @Autowired
    LoginService loginService;

    @PostMapping("login")
    // public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
    //     return loginService.login(loginRequestDto);
    // }
    public ResponseEntity<Object> login(@RequestBody LoginRequestDto dto){
        try {
            LoginResponseDto response = loginService.login(dto);
            return ResponseEntity.ok()
                .body(GenericResponse.success(response,
                 "Succesfully Login !"));
        }catch(ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode())
            .body(GenericResponse.error(e.getReason()));
        } 
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(GenericResponse.error(e.getMessage()));
        }
    }

}
