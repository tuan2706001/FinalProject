package com.java.project3.api;

import com.java.project3.dto.GradeDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.service.GradeServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grade")
public class ApiGradeController {
    @Autowired
    GradeServcie gradeServcie;

    @PostMapping("/create")
    public ResponseDto create(@RequestBody GradeDTO gradeDTO) {
        return gradeServcie.create(gradeDTO);
    }
}
