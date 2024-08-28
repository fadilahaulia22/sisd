package com.sisd.sisd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisd.sisd.entity.Users;


public interface UsersRepository extends JpaRepository<Users,String>{
    Optional<Users> findByUserName(String userName);
}
