package com.sisd.sisd.init;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.sisd.sisd.constant.RolesConstant;
import com.sisd.sisd.entity.Roles;
import com.sisd.sisd.repository.RolesRepository;

@Component // komponen spring yang general
public class InitialDataLoader implements ApplicationRunner{
 
    @Autowired
    RolesRepository rolesRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception{

        List<Roles> roles = rolesRepository.findAll();
        if (roles.isEmpty()) {
            // Roles headmaster = new Roles(null, "HEADMASTER", "Role as Headmaster in Application");
            // Roles parent = new Roles(null, "PARENT", "Role as Parent in Application");
            // Roles teacher = new Roles(null, "TEACHER", "Role as Teacher in Application");

            //KARENA DAH PAKE CONSTANT
            Roles headmaster = new Roles(null,RolesConstant.HEADMASTER_ROLE,"role as headmaster in application");
            Roles parent = new Roles(null,RolesConstant.PARENT_ROLE,"role as parent in application");
            Roles teacher = new Roles(null,RolesConstant.TEACHER_ROLE,"role as teacher in application");
            rolesRepository.saveAll(List.of(headmaster,parent,teacher));
        }
    }
}
