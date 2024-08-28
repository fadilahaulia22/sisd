package com.sisd.sisd.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.sisd.sisd.dto.PageResponse;
import com.sisd.sisd.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class StudentDaoImpl implements StudentDao{
    @Autowired
    EntityManager entityManager;

    @Override
    public PageResponse<Student> findAll(String nisn, String studentname, String registrationDate, int page, int size)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//total per page
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> studentRoot = criteriaQuery.from(Student.class);
        criteriaQuery.where(createPredicate(criteriaBuilder,studentRoot,nisn,studentname,registrationDate));
        List<Student> result = entityManager.createQuery(criteriaQuery)
        .setFirstResult((page -1 ) * size)
        .setMaxResults(size)
        .getResultList();
// total items
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Student> studentrootCount = countQuery.from(Student.class);
        countQuery.select(criteriaBuilder.count(studentrootCount))
        .where(createPredicate(criteriaBuilder,studentrootCount,nisn,studentname,registrationDate));
        Long totalItem = entityManager.createQuery(countQuery).getSingleResult();

        return PageResponse.success(result, page, size, size);   
    }

    private Predicate[] createPredicate(CriteriaBuilder criteriaBuilder, Root<Student> root, String nisn, String studentName, String registerDate){
        List<Predicate> predicates = new ArrayList<>();

        if (nisn != null && !nisn.isBlank() && !nisn.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("nisn"),"%" + nisn+"%"));
        }
        if (studentName != null && !studentName.isBlank() && !studentName.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("studentName"),"%" + studentName+"%"));
        }
        if (registerDate != null && !registerDate.isBlank() && !registerDate.isEmpty()) {
            try {
                LocalDate localDate = LocalDate.parse(registerDate,DateTimeFormatter.ISO_DATE);
                predicates.add(criteriaBuilder.equal(root.get("registerDate"),localDate));
            } catch (DateTimeParseException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Date Fromat");
            }
        }
        return predicates.toArray(new Predicate[0]);
    }
}
