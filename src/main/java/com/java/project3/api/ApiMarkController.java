package com.java.project3.api;

import com.java.project3.dto.MarkDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.service.MarkService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mark")
public class ApiMarkController {
    @Autowired
    MarkService markService;

    @PostMapping("/create")
    public ResponseDto create(@RequestBody MarkDTO markDTO) {
        return markService.create(markDTO);
    }

    @GetMapping("")
    public ResponseDto find(@RequestParam("grade_id") Long gradeId,
                            @RequestParam("subject_id") Long subjectId) {
        return markService.findByGradeAndSubject(gradeId, subjectId);
    }
}
