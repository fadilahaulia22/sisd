package com.sisd.sisd.entity;

import java.util.Date;

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
@Table(name = "classroom_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomHistory {
     @Id
    @UuidGenerator
    @Column(name = "id", length = 36, nullable =  false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "classsroom_id", referencedColumnName = "id", nullable = false)
    private Classroom classroom;

   @Column(name = "start_date")
   private Date startDate;
   
   @Column(name = "end_date")
   private Date endDate;

}
