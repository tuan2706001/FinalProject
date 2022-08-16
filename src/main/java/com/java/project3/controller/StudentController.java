package com.java.project3.controller;

import com.java.project3.dto.StudentDTO;
import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.dto.base.SearchResDto;
import com.java.project3.service.CourseClassServcie;
import com.java.project3.service.StudentService;
import com.java.project3.utils.PageUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    CourseClassServcie courseClassServcie;

    @GetMapping("quan-ly-sinh-vien")
    public String major(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "courseClassId", required = false) Long courseClassId
    ) {
        if ( courseClassId == null || courseClassId == 0 ) {
            courseClassId = null;
        }
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = studentService.searchStudentBy(page.getCurrentPage() - 1, page.getPageSize(), search, courseClassId);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);

        //get lớp
        SearchReqDto searchReqDtoLop = new SearchReqDto();
        searchReqDtoLop.setPageSize(100);
        searchReqDtoLop.setPageIndex(0);
        ResponseDto responseDtolop = courseClassServcie.search(searchReqDtoLop);
        SearchResDto searchResDtoLop = (SearchResDto) responseDtolop.getObject();
        model.addAttribute("lop", searchResDtoLop.getData());
        model.addAttribute("courseClassIds", courseClassId);

        return "quan-ly-sinh-vien";
    }

    @PostMapping("createStudent")
    public String createStudent(
            @ModelAttribute StudentDTO studentDTO
    ) {
        ResponseDto responseDto = studentService.create(studentDTO);
        return "redirect:/quan-ly-sinh-vien";
    }

    @GetMapping("/quan-ly-sinh-vien/{id}")
    public  String suaSinhVien (
            @PathVariable("id") Long id,
            Model model
    ) {
        //get lớp tôi sai chính tả r
        SearchReqDto searchReqDtoLop = new SearchReqDto();
        searchReqDtoLop.setPageSize(100);
        searchReqDtoLop.setPageIndex(0);
        ResponseDto responseDtolop = courseClassServcie.search(searchReqDtoLop);
        SearchResDto searchResDtoLop = (SearchResDto) responseDtolop.getObject();
        model.addAttribute("lop", searchResDtoLop.getData());

        ResponseDto responseDto =  studentService.findById(id);
        model.addAttribute("data", responseDto.getObject());
        return "fragment/body/home/edit/edit-student";
    }

    @PutMapping("updateStudent")
    public String updateStudent(
            @ModelAttribute StudentDTO studentDTO
    ) {
        ResponseDto responseDto = studentService.update(studentDTO);
        return "redirect:/quan-ly-sinh-vien";
    }

    @DeleteMapping("deleteStudent")
    public String deleteStudent(
            @RequestParam(name = "id") Long id
    ) {
        ResponseDto responseDto = studentService.delete(id);
        return "redirect:/quan-ly-sinh-vien";
    }
}
