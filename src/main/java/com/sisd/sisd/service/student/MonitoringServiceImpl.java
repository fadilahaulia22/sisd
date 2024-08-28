package com.sisd.sisd.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sisd.sisd.dao.StudentDao;
import com.sisd.sisd.dto.PageResponse;
import com.sisd.sisd.entity.Student;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MonitoringServiceImpl implements MonitoringService{
    @Autowired
    StudentDao studentDao;

    @Override
    public PageResponse<Student> findAll(String nisn, String studentname, String registerDate, int page, int size){
        return studentDao.findAll(nisn,studentname,registerDate,page,size);
    }
}
