package com.sisd.sisd.service.student;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.sisd.sisd.constant.RolesConstant;
import com.sisd.sisd.dto.StudentRegistrationRequestDto;
import com.sisd.sisd.entity.Parent;
import com.sisd.sisd.entity.Roles;
import com.sisd.sisd.entity.Student;
import com.sisd.sisd.entity.Users;
import com.sisd.sisd.repository.ParentRepository;
import com.sisd.sisd.repository.RolesRepository;
import com.sisd.sisd.repository.StudentRepository;
import com.sisd.sisd.repository.UsersRepository;
import com.sisd.sisd.service.EmailService;


@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService{
    @Autowired
    RolesRepository repository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ParentRepository parentRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    EmailService emailService;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    // @Transactional
    // @Override
    // public String register(StudentRegistrationRequestDto dto){
    //     // create user for parent
    //     Users users = new Users();
    //     users.setUserName(dto.getEmail());
    //     users.setPassword(passwordEncoder.encode(dto.getPassword()));
    //     //generate default password
    //     // String defaultPassword ="sisd2024";
    //     // users.setPassword(defaultPassword);

    //     //get role for parent
    //     Roles parentRoles = repository.findByRoleName(RolesConstant.PARENT_ROLE);
    //     users.setRoles(parentRoles);
    //     usersRepository.save(users);
        
        
    //     //create parent
    //     Parent parent =new Parent();
    //     parent.setParentName(dto.getParentName());
    //     parent.setAddress(dto.getAddress());
    //     parent.setEmail(dto.getEmail());
    //     parent.setPhoneNumber(dto.getPhoneNumber());
    //     parent.setProfession(dto.getProfession());
    //     parent.setUsers(users);
    //     //skip for photo (file processing) 
    //     parentRepository.save(parent);
        
        
        
    //     // create student
    //     Student student = new Student();
    //     student.setStudentName(dto.getStudentName());
    //     LocalDate dateOfBirth = LocalDate.parse(dto.getDateOfBirth(),
    //             DateTimeFormatter.ISO_DATE);
    //         student.setDateOfBirth(dateOfBirth);
    //         student.setPlaceOfBirth(dto.getPlaceOfBirth());
    //         student.setGender(dto.getGender());
    //         student.setRegisterDate(LocalDate.now());
    //         //skip fotr photo(file processing)
    //         student.setParent(parent);
    //         studentRepository.save(student);

    //   //  ketika parent register dia dapt user, dia bakal kirim email ke parent, isi : users
    //         String to = dto.getEmail();
    //         String subject = "Student Registration";
    //         String text = "Teruntuk orang tua siswa,\n \n" + 
    //         "Proses pendaftaran siswa sudah kami terima, " + 
    //         "selanjutnya kami akan informasikan bertahap. \n \n" +
    //         "Terima Kaaasihh. \n \n " +
    //         "Hormat, \n" +
    //         "Kepala Sekolah";
    //     emailService.sendSimpleMessage(to,subject,text);
        
    //     return "Registration Succesfully";
    // }


    @Override
     //ketika proses gagal, maka data tidak akan di proses
    public Student register(StudentRegistrationRequestDto dto){
        Users user = saveUsers(dto);
        Parent parent = saveParent(dto, user);
        Student student = saveStudent(dto, parent);
        sendEmail(dto.getEmail());
        return student;
    }

    private Student saveStudent(StudentRegistrationRequestDto dto, Parent parent){
        Student student = new Student();
        student.setStudentName(dto.getStudentName());
        LocalDate dateOfBirth = LocalDate.parse(dto.getDateOfBirth(),
                DateTimeFormatter.ISO_DATE);
        student.setDateOfBirth(dateOfBirth);
        student.setPlaceOfBirth(dto.getPlaceOfBirth());
        student.setGender(dto.getGender());
        student.setRegisterDate(LocalDate.now());
        student.setParent(parent);
        return studentRepository.save(student);
    }
    private Users saveUsers(StudentRegistrationRequestDto dto){
        Users users = new Users();
        users.setUserName(dto.getEmail());
        users.setPassword(passwordEncoder.encode(dto.getPassword()));
        Roles parentRoles = repository.findByRoleName(RolesConstant.PARENT_ROLE);
        users.setRoles(parentRoles);
        return usersRepository.save(users);
    }

    private Parent saveParent(StudentRegistrationRequestDto dto, Users users){
        Parent parent = new Parent();
        parent.setParentName(dto.getParentName());
        parent.setAddress(dto.getAddress());
        parent.setEmail(dto.getEmail());
        parent.setPhoneNumber(dto.getPhoneNumber());
        parent.setProfession(dto.getProfession());
        parent.setUsers(users);
        return parentRepository.save(parent);
    }

    private void sendEmail(String to){
        String subject = "Student Registration";
            String text = "Teruntuk orang tua siswa,\n \n" + 
            "Proses pendaftaran siswa sudah kami terima, " + 
            "selanjutnya kami akan informasikan bertahap. \n \n" +
            "Terima Kaaasihh. \n \n " +
            "Hormat, \n" +
            "Kepala Sekolah";
         emailService.sendSimpleMessage(to,subject,text);
    }


    // PHOTO

    @Override
    public void uploadStudentPhoto(String studentId, MultipartFile studentPhoto) throws IOException, SQLException {
        
        String[] filename = Objects.requireNonNull(studentPhoto.getResource().getFilename()).split("\\.");

        if (!filename[filename.length - 1].equalsIgnoreCase("jpg")
                && !filename[filename.length - 1].equalsIgnoreCase("jpeg")
                && !filename[filename.length - 1].equalsIgnoreCase("png")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported file type");
        }

        System.out.println(filename);

        @SuppressWarnings("null")

        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            student.setPhoto(new SerialBlob(studentPhoto.getBytes()));
            studentRepository.save(student);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student not Found");
        }
    }

            @Override
            public void uploadParentPhoto(String parentId, MultipartFile parentPhoto)
            throws IOException, SQLException {
                Parent parent = parentRepository.findById(parentId).orElse(null);
                if (parent != null) {
                    parent.setPhoto(new SerialBlob(parentPhoto.getBytes()));
                    parentRepository.save(parent);
                }else{
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent not found");
                }
            }
}

