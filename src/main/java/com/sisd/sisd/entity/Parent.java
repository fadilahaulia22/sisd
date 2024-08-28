package com.sisd.sisd.entity;

import java.sql.Blob;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parent")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parent {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 36, nullable =  false)
    private String id;

    @Column(name = "parent_name")
    private String parentName;
    
    @Column(name = "address", length = 2000)
    private String address;

    @Column(name = "profession", length = 25)
    private String profession;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "photo")
    private Blob photo;

    @OneToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Users users;

}
