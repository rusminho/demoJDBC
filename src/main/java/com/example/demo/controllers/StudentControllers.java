package com.example.demo.controllers;

import com.example.demo.helpers.ApiResponse;
import com.example.demo.models.Student;
import com.example.demo.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentControllers {

    @Autowired
    private StudentRepo studentRepo;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<String>> saveStudent(@RequestBody Student student) {


        try {
            studentRepo.save(student);
            // ApiResponse with status and message
            ApiResponse<String> response = new ApiResponse<>("201 Created", "Student saved successfully!");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // Error response with message
            ApiResponse<String> response = new ApiResponse<>("500 Internal Server Error", "Failed to save student");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        try {
            List<Student> students = studentRepo.findAll();

            if (students.isEmpty()) {
                ApiResponse<List<Student>> response = new ApiResponse<>("204 No Content", null);
                return new ResponseEntity<>(response, HttpStatus.OK);  // workaround, since 204 not have a body;
            }

            ApiResponse<List<Student>> response = new ApiResponse<>("200 OK", students);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<Student>> response = new ApiResponse<>("500 Internal Server Error", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
