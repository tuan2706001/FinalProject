package com.java.project3.controller;

import com.java.project3.dto.GradeDTO;
import com.java.project3.dto.MarkDTO;
import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.dto.base.SearchResDto;
import com.java.project3.service.GradeServcie;
import com.java.project3.service.MarkService;
import com.java.project3.service.StudentService;
import com.java.project3.service.SubjectService;
import com.java.project3.utils.PageUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MarkController {
    @Autowired
    MarkService markService;
    @Autowired
    GradeServcie gradeServcie;
    @Autowired
    StudentService studentService;
    @Autowired
    SubjectService subjectService;

    @GetMapping("quan-ly-diem")
    public String major(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "gradeId", required = false) Long gradeId,
            @RequestParam(value = "studentId", required = false) Long studentId,
            @RequestParam(value = "subjectId", required = false) Long subjectId
    ) {
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = markService.searchMarkBy(page.getCurrentPage() - 1, page.getPageSize(), search, gradeId, studentId, subjectId);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);

        //get lớp
        SearchReqDto searchReqDtoLop = new SearchReqDto();
        searchReqDtoLop.setPageSize(100);
        searchReqDtoLop.setPageIndex(0);
        ResponseDto responseDtolop = gradeServcie.search(searchReqDtoLop);
        SearchResDto searchResDtoLop = (SearchResDto) responseDtolop.getObject();
        model.addAttribute("lop", searchResDtoLop.getData());
        model.addAttribute("gradeId", gradeId);

        //get sinh vien
        SearchReqDto searchReqDtoSinhVien = new SearchReqDto();
        searchReqDtoSinhVien.setPageSize(100);
        searchReqDtoSinhVien.setPageIndex(0);
        ResponseDto responseDtoSinhVien = studentService.search(searchReqDtoLop);
        SearchResDto searchResDtoSinhVien = (SearchResDto) responseDtoSinhVien.getObject();
        model.addAttribute("student", searchResDtoSinhVien.getData());
        model.addAttribute("studentId", studentId);

        //get mon hoc
        SearchReqDto searchReqDtoMon = new SearchReqDto();
        searchReqDtoMon.setPageSize(100);
        searchReqDtoMon.setPageIndex(0);
        ResponseDto responseDtoMon = subjectService.search(searchReqDtoMon);
        SearchResDto searchResDtoMon = (SearchResDto) responseDtoMon.getObject();
        model.addAttribute("mon", searchResDtoMon.getData());
        model.addAttribute("subjectId", subjectId);

        return "quan-ly-diem";
    }

    @PostMapping("createMark")
    public String createMark(
            @ModelAttribute MarkDTO markDTO
    ) {
        ResponseDto responseDto = markService.create(markDTO);
        return "redirect:/quan-ly-diem";
    }

    @GetMapping("/quan-ly-diem/{id}")
    public  String suaDien (
            @PathVariable("id") Long id,
            Model model
    ) {
        ResponseDto responseDto =  markService.findById(id);
        model.addAttribute("data", responseDto.getObject());
        return "fragment/body/home/edit/edit-mark";
    }

    @PutMapping("updateMark")
    public String updateMark(
            @ModelAttribute MarkDTO markDTO
    ) {
        ResponseDto responseDto = markService.update(markDTO);
        return "redirect:/quan-ly-diem";
    }

    @DeleteMapping("deleteMark")
    public String deleteMark(
            @RequestParam(name = "id") Long id
    ) {
        ResponseDto responseDto = markService.delete(id);
        return "redirect:/quan-ly-diem";
    }


}
