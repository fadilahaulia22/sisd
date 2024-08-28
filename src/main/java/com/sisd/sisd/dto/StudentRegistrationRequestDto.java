package com.sisd.sisd.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRegistrationRequestDto {
    //student
    String studentName;
    String dateOfBirth; //"1990-10-25" YYYY-MM-DD ISO_8601
    String placeOfBirth;
    String gender;
    String studentPhoto;

    //parent
    String parentName;
    String address;
    String email;
    String phoneNumber;
    String profession;
    String parentPhoto;

    //login
    String password;
}
