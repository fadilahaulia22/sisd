package com.sisd.sisd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisd.sisd.entity.Student;

public interface StudentRepository extends JpaRepository<Student,String>{
    
}
