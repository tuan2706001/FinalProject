package com.java.project3.api;

import com.java.project3.dto.GradeDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.service.GradeServcie;
import com.java.project3.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grade")
public class ApiGradeController {
    @Autowired
    GradeServcie gradeServcie;
    @Autowired
    SubjectService subjectService;

    @PostMapping("/create")
    public ResponseDto create(@RequestBody GradeDTO gradeDTO) {
        return gradeServcie.create(gradeDTO);
    }

    @PutMapping("/update")
    public ResponseDto update(@RequestBody GradeDTO gradeDTO) {
        return gradeServcie.update(gradeDTO);
    }

    @GetMapping("")
    public ResponseDto find(@RequestParam(name = "grade_id") Long gradeId) {
        return subjectService.findByGradeId(gradeId);
    }

    @PostMapping("/count")
    public long count() {
        return gradeServcie.countAll();
    }
}
