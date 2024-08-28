package com.sisd.sisd.dao;

import com.sisd.sisd.dto.PageResponse;
import com.sisd.sisd.entity.Student;

public interface StudentDao {
    PageResponse<Student> findAll(String nisn, String studentName,String registerDate,int page ,int size);
}
