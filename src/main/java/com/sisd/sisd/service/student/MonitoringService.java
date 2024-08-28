package com.sisd.sisd.service.student;

import com.sisd.sisd.dto.PageResponse;
import com.sisd.sisd.entity.Student;

public interface MonitoringService {
    PageResponse<Student> findAll(String nisn, String studentName,String registerDate,int page ,int size);
    
}
