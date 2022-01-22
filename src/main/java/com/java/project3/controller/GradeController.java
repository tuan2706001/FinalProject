package com.java.project3.controller;

import com.java.project3.dto.GradeDTO;
import com.java.project3.dto.MajorDTO;
import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.dto.base.SearchResDto;
import com.java.project3.service.CourseServcice;
import com.java.project3.service.GradeServcie;
import com.java.project3.service.MajorService;
import com.java.project3.utils.PageUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class GradeController {
    @Autowired
    GradeServcie gradeServcie;
    @Autowired
    CourseServcice courseServcice;
    @Autowired
    MajorService majorService;

    @GetMapping("quan-ly-lop")
    public String major(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search
    ) {
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = gradeServcie.searchGradeBy(page.getCurrentPage() - 1, page.getPageSize(), search);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);

        //get khóa
        SearchReqDto searchReqDtoKhoa = new SearchReqDto();
        searchReqDtoKhoa.setPageSize(100);
        searchReqDtoKhoa.setPageIndex(0);
        ResponseDto responseDtoKhoa = courseServcice.search(searchReqDtoKhoa);
        SearchResDto searchResDtoKhoa = (SearchResDto) responseDtoKhoa.getObject();
        model.addAttribute("khoas", searchResDtoKhoa.getData());

        //get ngành
        SearchReqDto searchReqDtoNganh = new SearchReqDto();
        searchReqDtoKhoa.setPageSize(100);
        searchReqDtoKhoa.setPageIndex(0);
        ResponseDto responseDtoNganh = majorService.search(searchReqDtoKhoa);
        SearchResDto searchResDtoNganh = (SearchResDto) responseDtoNganh.getObject();
        model.addAttribute("nganh", searchResDtoNganh.getData());

        return "quan-ly-lop";
    }

    @PostMapping("createGrade")
    public String createGrade(
            @ModelAttribute GradeDTO gradeDTO
    ) {
        ResponseDto responseDto = gradeServcie.create(gradeDTO);
        return "redirect:/quan-ly-lop";
    }

    @PostMapping("updateGrade")
    public String updateGrade(
            @ModelAttribute GradeDTO gradeDTO
    ) {
        ResponseDto responseDto = gradeServcie.update(gradeDTO);
        return "redirect:/quan-ly-lop";
    }
}
