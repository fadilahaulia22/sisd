package com.sisd.sisd.service.student;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.web.multipart.MultipartFile;

import com.sisd.sisd.dto.StudentRegistrationRequestDto;
import com.sisd.sisd.entity.Student;

public interface RegistrationService {
    Student register(StudentRegistrationRequestDto dto);

    void uploadStudentPhoto(String studentId, MultipartFile photo)
        throws IOException, SQLException;
    
    void uploadParentPhoto(String parentId, MultipartFile photo)
            throws IOException, SQLException;
            
}


