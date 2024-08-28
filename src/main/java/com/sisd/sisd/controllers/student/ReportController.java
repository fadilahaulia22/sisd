package com.sisd.sisd.controllers.student;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisd.sisd.constant.MessageConstant;
import com.sisd.sisd.dto.GenericResponse;
import com.sisd.sisd.service.student.ReportService;

@RestController
@RequestMapping("/student")
@Tag(name = "student")
@Slf4j
public class ReportController {
    
    @Autowired
    ReportService reportService;
    
    @GetMapping("/report")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> report(HttpServletResponse response){
        try {
            response.setHeader("Content-Disposition",
                    "attachment: filename=report.xlsx");
            return ResponseEntity.ok(reportService.generateReport());
        }catch (Exception e){
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GenericResponse.error(MessageConstant.ERROR_500));
        }
    }
}