package com.java.project3.api;

import com.java.project3.dto.GradeDTO;
import com.java.project3.dto.StudentDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.service.StudentService;
import com.java.project3.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/student")
public class ApiStudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    SubjectService subjectService;

    @PostMapping("/create")
    public ResponseDto create(@RequestBody StudentDTO studentDTO) {
        return studentService.create(studentDTO);
    }

    @PostMapping("/search")
    public ResponseDto search(@RequestParam(name = "page_index", required = false) Integer pageIndex,
                              @RequestParam(name = "page_size", required = false) Integer pageSize,
                              @RequestParam(name = "name", required = false) String name,
                              @RequestParam(name = "type", required = false) String type) {
        return subjectService.searchSubjectByType1(type, name, pageSize, pageIndex);
    }


}
