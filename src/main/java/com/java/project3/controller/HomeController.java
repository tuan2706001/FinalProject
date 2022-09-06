package com.java.project3.controller;

import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchResDto;
//import com.java.project3.service.CourseClassServcie;
//import com.java.project3.service.StudentService;
import com.java.project3.service.*;
import com.java.project3.utils.PageUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    StudentService studentService;
    @Autowired
    UserService userService;
    @Autowired
    CourseClassServcie gradeServcie;
    @Autowired
    CourseServcice courseServcice;
    @Autowired
    CurriculumService curriculumService;
    @Autowired
    MajorService majorService;
    @Autowired
    CtdtService ctdtService;
    @Autowired
    CtdtSubjectClassService ctdtSubjectClassService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    SubjectService subjectService;

    @GetMapping(value = {"","/trang-chu"})
    public String home(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "gradeId", required = false) Long gradeId

    ) {
        if (gradeId == null || gradeId == 0) {
            gradeId = null;
        }
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = studentService.searchStudentBy(page.getCurrentPage() - 1, page.getPageSize(), search, gradeId);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);


        long responseDto1 = gradeServcie.countAll();
        model.addAttribute("countAllGrade", responseDto1);

        long responseDto2 = studentService.countAll();
        model.addAttribute("countAllStudent", responseDto2);

        long responseDto3 = courseServcice.countAll();
        model.addAttribute("countAllCourse", responseDto3);

        long responseDto4 = curriculumService.countAll();
        model.addAttribute("countAllCurriculum", responseDto4);

        long responseDto5 = majorService.countAll();
        model.addAttribute("countAllMajor", responseDto5);

        long responseDto6 = ctdtService.countAll();
        model.addAttribute("countAllCtdt", responseDto6);

        long responseDto7 = studentService.countAll();
        model.addAttribute("countAllSubject", responseDto7);

        long responseDto8 = teacherService.countAll();
        model.addAttribute("countAllTeacher", responseDto8);

        long responseDto9 = ctdtSubjectClassService.countAll();
        model.addAttribute("countAllCtdtSubjectClass", responseDto9);

        return "trang-chu";
    }

    @GetMapping("thong-tin-ca-nhan")
    public String info() {
        return "thong-tin-ca-nhan";
    }




//    @GetMapping("/thong-tin-ca-nhan/{id}")
//    public  String suaInfo (
//            @PathVariable("id") Long id,
//            Model model
//    ) {
//        ResponseDto responseDto =  userService.findById(id);
//        model.addAttribute("data", responseDto.getObject());
//        return "fragment/body/home/thong-tin-ca-nhan";
//    }
//
//    @PutMapping("updateInfo")
//    public String updateInfo(
//            @ModelAttribute UserDTO userDTO
//    ) {
//        ResponseDto responseDto = userService.update(userDTO);
//        return "redirect:/thong-tin-ca-nhan";
//    }



    @GetMapping("/dang-nhap")
    public String login() {
        return "dang-nhap";
    }

    @GetMapping("dang-ky")
    public String signUp() {
        return "dang-ky";
    }




}
