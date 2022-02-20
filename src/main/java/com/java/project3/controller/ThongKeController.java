package com.java.project3.controller;

import com.java.project3.dto.GradeDTO;
import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.dto.base.SearchResDto;
import com.java.project3.service.*;
import com.java.project3.utils.PageUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class ThongKeController {
    @Autowired
    GradeServcie gradeServcie;
    @Autowired
    CourseServcice courseServcice;
    @Autowired
    MajorService majorService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    StudentService studentService;
    @Autowired
    MarkService markService;

    @GetMapping("thong-ke")
    public String thongKe(
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


        return "thong-ke";
    }

    @GetMapping("thong-ke-mon")
    public String major(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search
    ) {
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = subjectService.searchSubjectBy(page.getCurrentPage() - 1, page.getPageSize(), search);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);

        return "thong-ke-mon";
    }

    @GetMapping("thong-ke-diem")
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

        return "thong-ke-diem";
    }


}
