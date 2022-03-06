package com.java.project3.controller;

import com.java.project3.dto.GradeDTO;
import com.java.project3.dto.MajorDTO;
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
import static com.java.project3.constant.UriConstans.THONG_KE_DIEM;

import java.util.List;

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
        model.addAttribute("url", THONG_KE_DIEM);


        //get khóa
        SearchReqDto searchReqDtoKhoa = new SearchReqDto();
        searchReqDtoKhoa.setPageSize(100);
        searchReqDtoKhoa.setPageIndex(0);
        ResponseDto responseDtoKhoa = courseServcice.search(searchReqDtoKhoa);
        SearchResDto searchResDtoKhoa = (SearchResDto) responseDtoKhoa.getObject();
        model.addAttribute("khoa", searchResDtoKhoa.getData());
        model.addAttribute("courseId", courseId);


        //get ngành
        SearchReqDto searchReqDtoNganh = new SearchReqDto();
        searchReqDtoKhoa.setPageSize(100);
        searchReqDtoKhoa.setPageIndex(0);
        ResponseDto responseDtoNganh = majorService.search(searchReqDtoKhoa);
        SearchResDto searchResDtoNganh = (SearchResDto) responseDtoNganh.getObject();
        model.addAttribute("nganh", searchResDtoNganh.getData());
        model.addAttribute("majorId", majorId);

        //dùng ajax
        List<MajorDTO> majorDTOS = null;
        if (majorId != null) {
            ResponseDto responseDto1 = majorService.findById(majorId);
            MajorDTO majorDTO = (MajorDTO) responseDto1.getObject();
            model.addAttribute("courseId", majorDTO.getCourseId());

            ResponseDto responseDto2 = majorService.findByCourseId(majorDTO.getCourseId());
            majorDTOS = (List<MajorDTO>) responseDto2.getObject();
            model.addAttribute("majorIds", majorId);

        }
        model.addAttribute("dataNganh", majorDTOS);

        return "thong-ke";
    }

    @GetMapping("getMajors/{id}")
    @ResponseBody
    public List<MajorDTO> getMajor(
            @PathVariable("id") Long id
    ) {
        ResponseDto responseDto = majorService.findByCourseId(id);
        return (List<MajorDTO>) responseDto.getObject();
    }

    @GetMapping("thong-ke-mon")
    public String major(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "type", required = false) String type
    ) {
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = subjectService.searchSubjectBy(page.getCurrentPage() - 1, page.getPageSize(), search, type);
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
        ResponseDto responseDto = markService.findByGradeId(page.getCurrentPage() - 1, page.getPageSize(), search, gradeId, subjectId);
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
