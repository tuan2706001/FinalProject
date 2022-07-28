//package com.java.project3.api;
//
//import com.java.project3.dto.CourseClassDTO;
//import com.java.project3.dto.base.ResponseDto;
//import com.java.project3.service.CourseClassServcie;
//import com.java.project3.service.SubjectService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/grade")
//public class ApiGradeController {
//    @Autowired
//    CourseClassServcie gradeServcie;
//    @Autowired
//    SubjectService subjectService;
//
//    @PostMapping("/create")
//    public ResponseDto create(@RequestBody CourseClassDTO courseClassDTO) {
//        return gradeServcie.create(courseClassDTO);
//    }
//
//    @PutMapping("/update")
//    public ResponseDto update(@RequestBody CourseClassDTO courseClassDTO) {
//        return gradeServcie.update(courseClassDTO);
//    }
//
//    @GetMapping("")
//    public ResponseDto find(@RequestParam(name = "grade_id") Long gradeId) {
//        return subjectService.findByGradeId(gradeId);
//    }
//
//    @PostMapping("/count")
//    public long count() {
//        return gradeServcie.countAll();
//    }
//}
