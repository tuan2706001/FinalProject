//package com.java.project3.api;
//
//import com.java.project3.dto.MarkDTO;
//import com.java.project3.dto.base.ResponseDto;
//import com.java.project3.service.MarkService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/mark")
//public class ApiMarkController {
//    @Autowired
//    MarkService markService;
//
//    @PostMapping("/create")
//    public ResponseDto create(@RequestBody MarkDTO markDTO) {
//        return markService.create(markDTO);
//    }
//
//    @GetMapping("")
//    public ResponseDto find(@RequestParam(name = "page_index", required = false) Integer pageIndex,
//                            @RequestParam(name = "page_size", required = false) Integer pageSize,
//                            @RequestParam(name = "grade_id", required = false) Long gradeId,
//                            @RequestParam(name = "subject_id", required = false) Long subjectId,
//                            @RequestParam(name = "search", required = false) String search) {
//        return markService.findByGradeId(pageIndex, pageSize, search, gradeId, subjectId);
//    }
//}
