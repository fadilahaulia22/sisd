package com.sisd.sisd.controllers.student;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.sisd.sisd.constant.MessageConstant;
import com.sisd.sisd.dto.GenericResponse;
import com.sisd.sisd.dto.StudentRegistrationRequestDto;
import com.sisd.sisd.entity.Student;
import com.sisd.sisd.service.student.RegistrationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/student")
@Tag(name = "student")
@Slf4j
public class RegistrationController {
    @Autowired
    RegistrationService registrationService;

    // @PostMapping("/register") //harus verb
    // public String register(@RequestBody StudentRegistrationRequestDto dto){
    //     return registrationService.register(dto);
    // }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody StudentRegistrationRequestDto dto){
        try{
            Student student = registrationService.register(dto);
            return ResponseEntity.ok()
                    .body(GenericResponse.success(student, 
                    "Successfully register new student"));
        }catch(Exception e){
            return ResponseEntity.internalServerError()
                    .body(GenericResponse.error(e.getMessage()));

        }
    }

    @PostMapping(value = "/upload-student-photo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadStudentPhoto(@RequestParam String studentId,@RequestParam("studentPhoto") MultipartFile file){
        try {
            registrationService.uploadStudentPhoto(studentId,file);
            return ResponseEntity.ok().body(GenericResponse.success(null, "successfully upload student photo"));
        }
        catch (ResponseStatusException e){
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getReason()));
        }
         catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(GenericResponse.error(MessageConstant.ERROR_500));
        }
    }

        @PostMapping(value = "/upload-parent-photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<Object> uploadParentPhoto(@RequestParam String parentId,
            @RequestParam("parentPhoto") MultipartFile file){
                try {
                    registrationService.uploadParentPhoto(parentId,file);
                    return ResponseEntity.ok()
                            .body(GenericResponse.success(null, "Successfully upload parent photo"));
                } catch (ResponseStatusException e) {
                    log.info(e.getMessage());
                    return ResponseEntity.status(e.getStatusCode())
                        .body(GenericResponse.error(e.getReason()));
                }catch(Exception e){
                    log.info(e.getMessage());
                    return ResponseEntity.internalServerError()
                        .body(GenericResponse.error(MessageConstant.ERROR_500));
                }
            }
}