package com.java.project3.controller;

import com.java.project3.dto.GradeDTO;
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
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "courseId", required = false) Long courseId,
            @RequestParam(value = "majorId", required = false) Long majorId
    ) {
        if ( majorId == null || majorId == 0 ) {
            majorId = null;
        }
        if (courseId == null || courseId == 0 ) {
            courseId = null;
        }
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = gradeServcie.searchGradeBy(page.getCurrentPage() - 1, page.getPageSize(), search, courseId, majorId);
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
        model.addAttribute("courseId", courseId);


        //get ngành
        SearchReqDto searchReqDtoNganh = new SearchReqDto();
        searchReqDtoKhoa.setPageSize(100);
        searchReqDtoKhoa.setPageIndex(0);
        ResponseDto responseDtoNganh = majorService.search(searchReqDtoKhoa);
        SearchResDto searchResDtoNganh = (SearchResDto) responseDtoNganh.getObject();
        model.addAttribute("nganh", searchResDtoNganh.getData());
        model.addAttribute("majorId", majorId);


        return "quan-ly-lop";
    }

    @PostMapping("createGrade")
    public String createGrade(
            @ModelAttribute GradeDTO gradeDTO
    ) {
        ResponseDto responseDto = gradeServcie.create(gradeDTO);
        return "redirect:/quan-ly-lop";
    }

    @GetMapping("quan-ly-lop/{id}")
    public  String suaLop (
            @PathVariable("id") Long id,
            Model model
    ) {
        //get khóa
        SearchReqDto searchReqDtoKhoa = new SearchReqDto();
        searchReqDtoKhoa.setPageSize(100);
        searchReqDtoKhoa.setPageIndex(0);
        ResponseDto responseDtoKhoa = courseServcice.search(searchReqDtoKhoa);
        SearchResDto searchResDtoKhoa = (SearchResDto) responseDtoKhoa.getObject();
        model.addAttribute("khoas", searchResDtoKhoa.getData());

        //get ngành
        SearchReqDto searchReqDtoNganh = new SearchReqDto();
        searchReqDtoNganh.setPageSize(100);
        searchReqDtoNganh.setPageIndex(0);
        ResponseDto responseDtoNganh = majorService.search(searchReqDtoNganh);
        SearchResDto searchResDtoNganh = (SearchResDto) responseDtoNganh.getObject();
        model.addAttribute("nganh", searchResDtoNganh.getData());

        ResponseDto responseDto =  gradeServcie.findById(id);
        model.addAttribute("data", responseDto.getObject());
        return "fragment/body/home/edit/edit-grade";
    }

    @PutMapping("updateGrade")
    public String updateGrade(
            @ModelAttribute GradeDTO gradeDTO
    ) {
        ResponseDto responseDto = gradeServcie.update(gradeDTO);
        return "redirect:/quan-ly-lop";
    }
}
