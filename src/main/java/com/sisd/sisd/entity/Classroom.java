package com.sisd.sisd.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "classroom")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classroom {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 36, nullable =  false)
    private String id;

    @Column(name = "classroom_name")
    private String classroomName;

    @Column(name = "nip",length = 25)
    private String nip;

    @Column(name = "location", length = 2000)
    private String location;

    @Column(name = "level", length =  50)
    private String level;

    @OneToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
    private Teacher teacher;

}
