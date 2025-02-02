package com.github.ms_student.service;

import com.github.ms_student.dto.School;
import com.github.ms_student.dto.StudentResponse;
import com.github.ms_student.model.Student;
import com.github.ms_student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<?> createStudent(Student student){
        try{
            return new ResponseEntity<Student>(studentRepository.save(student), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getStudentById(String id) {
        Optional<Student> student = studentRepository.findById(id);
        if(student.isPresent()) {
            School school = restTemplate.getForObject("http://localhost:8080/school/" + student.get().getSchoolId(), School.class);
            StudentResponse studentResponse = new StudentResponse(
                    student.get().getId(),
                    student.get().getName(),
                    student.get().getAge(),
                    student.get().getGender(),
                    school
            );
            return new ResponseEntity<>(studentResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No student found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getStudents() {
        List<Student> students = studentRepository.findAll();
        if(students.size() > 0) {
            return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No students", HttpStatus.NOT_FOUND);
        }
    }
}
