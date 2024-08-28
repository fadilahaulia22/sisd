package com.sisd.sisd.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assessment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assessment {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 36, nullable =  false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "classroom_id", referencedColumnName = "id", nullable = false)
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    private Subject subject;

    @Column(name = "subject_value")
    private Integer subjectValue;

    @Column(name = "subject_grade",length = 10)
    private String subjectGrade;
    
    @Column(name = "notes", length = 2000)
    private String notes;
}
