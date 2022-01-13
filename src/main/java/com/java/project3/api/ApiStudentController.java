package com.java.project3.api;

import com.java.project3.dto.GradeDTO;
import com.java.project3.dto.StudentDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class ApiStudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/create")
    public ResponseDto create(@RequestBody StudentDTO studentDTO) {
        return studentService.create(studentDTO);
    }
}
