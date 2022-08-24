package com.java.project3.controller;

import com.java.project3.dto.MajorDTO;
import com.java.project3.dto.StudentDTO;
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

import java.util.List;

import static com.java.project3.constant.UriConstans.*;

@Controller
@RequestMapping("/")
public class ThongKeController {
    @Autowired
    CourseClassServcie gradeServcie;
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
    @Autowired
    CtdtSubjectClassService ctdtSubjectClassService;
    @Autowired
    ThongKeSevice thongKeSevice;

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
        ResponseDto responseDto = gradeServcie.searchCourseClassBy(page.getCurrentPage() - 1, page.getPageSize(), search, courseId, majorId);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);
        model.addAttribute("url", THONG_KE_LOP_THEO_MON);


        return "thong-ke";
    }
    @GetMapping("thong-ke-lop-theo-mon/{courseClassId}")
    public String thongKeLopTheoMon(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search,
            @PathVariable(value = "courseClassId") Long courseClassId
    ) {
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = ctdtSubjectClassService.searchClassThongKeBy(page.getCurrentPage() - 1, page.getPageSize(), search, courseClassId);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);
        model.addAttribute("url", THONG_KE_DIEM);

        return "/thong-ke-lop-theo-mon";
    }

    @GetMapping("thong-ke-diem/{ctdtSubjectClassId}")
    public String thongKeDiem(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "gradeId", required = false) Long gradeId,
            @RequestParam(value = "studentId", required = false) Long studentId,
            @PathVariable(value = "ctdtSubjectClassId") Long ctdtSubjectClassId
    ) {
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = thongKeSevice.findByMark(page.getCurrentPage() - 1, page.getPageSize(), search, gradeId, ctdtSubjectClassId);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);

        return "/thong-ke-diem";
    }

    @GetMapping("getThongSoBieuDo/{ctdtSubjectClassId}")
    @ResponseBody
    public List<StudentDTO> getStudent(
            @PathVariable("id") Long id
    ) {
        ResponseDto responseDto = studentService.findByCscId(id);
        return (List<StudentDTO>) responseDto.getObject();
    }


}
