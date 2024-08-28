package com.sisd.sisd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisd.sisd.entity.Roles;


public interface RolesRepository extends JpaRepository<Roles,String> {
    Roles findByRoleName(String rolename);
}
