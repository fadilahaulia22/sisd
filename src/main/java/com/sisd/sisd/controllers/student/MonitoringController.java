package com.sisd.sisd.controllers.student;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sisd.sisd.dto.GenericResponse;
import com.sisd.sisd.service.student.MonitoringService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/student")
@Tag(name = "student")
@Slf4j
public class MonitoringController {

    @Autowired
    private MonitoringService monitoringService;
    
    @GetMapping("/find-all")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> findAll(@RequestParam(required = false) String nisn,
                                        @RequestParam(required = false) String studentname,
                                        @RequestParam(required = false) String registerDate,
                                        @RequestParam int page,
                                        @RequestParam int size){
        try{
            return ResponseEntity.ok()
                    .body(GenericResponse.success(monitoringService.findAll(nisn, studentname, registerDate, page, size), 
                    "Successfully Fect Data"));
        }catch(ResponseStatusException e){
            log.info(e.getMessage());

            return ResponseEntity.status(e.getStatusCode())
                    .body(GenericResponse.error(e.getReason()));
        }catch(Exception e){
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                .body(GenericResponse.error("Internal Server Error"));
        }
    }
}
    