package com.java.project3.controller;

import com.java.project3.domain.Ctdt;
import com.java.project3.domain.Major;
import com.java.project3.dto.CourseClassDTO;
import com.java.project3.dto.CtdtDTO;
import com.java.project3.dto.MajorDTO;
import com.java.project3.dto.TeacherDTO;
import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.dto.base.SearchResDto;
import com.java.project3.repository.CtdtRepository;
import com.java.project3.repository.MajorRepository;
import com.java.project3.service.CourseServcice;
import com.java.project3.service.CourseClassServcie;
import com.java.project3.service.CtdtService;
import com.java.project3.service.MajorService;
import com.java.project3.utils.PageUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/")
public class CourseClassController {
    @Autowired
    CourseClassServcie courseClassServcie;
    @Autowired
    CourseServcice courseServcice;
    @Autowired
    CtdtService ctdtService;
    @Autowired
    MajorService majorService;
    @Autowired
    MajorRepository majorRepository;
    @Autowired
    CtdtRepository ctdtRepository;

    @GetMapping("quan-ly-lop-chung")
    public String courseClass(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "courseId", required = false) Long courseId,
            @RequestParam(value = "ctdtId", required = false) Long ctdtId,
            @RequestParam(value = "majorId", required = false) Long majorId
    ) {
        if (ctdtId == null || ctdtId == 0) {
            ctdtId = null;
        }
        if (courseId == null || courseId == 0) {
            courseId = null;
        }
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = courseClassServcie.searchCourseClassBy(page.getCurrentPage() - 1, page.getPageSize(), search, courseId, ctdtId);
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
        model.addAttribute("khoa", searchResDtoKhoa.getData());
        model.addAttribute("courseId", courseId);
//
        //get nganh
        SearchReqDto searchReqDtoNganh = new SearchReqDto();
        searchReqDtoNganh.setPageSize(100);
        searchReqDtoNganh.setPageIndex(0);
        ResponseDto responseDtoNganh = majorService.search(searchReqDtoNganh);
        SearchResDto searchResDtoNganh = (SearchResDto) responseDtoNganh.getObject();
        model.addAttribute("nganh", searchResDtoNganh.getData());
        model.addAttribute("majorId", majorId);

        //dùng ajax lấy ctdt theo ngành
        List<CtdtDTO> ctdtDTOS = null;
        if (majorId != null) {
            ResponseDto responseDto1 = ctdtService.findById(ctdtId);
            CtdtDTO ctdtDTO = (CtdtDTO) responseDto1.getObject();
            model.addAttribute("majorId", ctdtDTO.getMajorId());

            ResponseDto responseDto2 = ctdtService.findByMajorId(ctdtDTO.getMajorId());
            ctdtDTOS = (List<CtdtDTO>) responseDto2.getObject();
            model.addAttribute("ctdtId", ctdtId);

        }
        model.addAttribute("dataCtdt", ctdtDTOS);


        return "quan-ly-lop-chung";
    }

    @PostMapping("createCourseClass")
    public String createCourseClass(
            Model model,
            @ModelAttribute CourseClassDTO courseClassDTO
    ) {
        ResponseDto responseDto = courseClassServcie.create(courseClassDTO);
        return "redirect:/quan-ly-lop-chung";
    }

    @GetMapping("quan-ly-lop-chung/{id}/{ctdtId}")
    public String suaLop(
            @PathVariable("id") Long id,
            @PathVariable("ctdtId") Long ctdtId,
            Model model
    ) {
        ResponseDto responseDto = courseClassServcie.findById(id);
        model.addAttribute("data", responseDto.getObject());


        //get khóa
        SearchReqDto searchReqDtoKhoa = new SearchReqDto();
        searchReqDtoKhoa.setPageSize(100);
        searchReqDtoKhoa.setPageIndex(0);
        ResponseDto responseDtoKhoa = courseServcice.search(searchReqDtoKhoa);
        SearchResDto searchResDtoKhoa = (SearchResDto) responseDtoKhoa.getObject();
        model.addAttribute("khoa", searchResDtoKhoa.getData());
        model.addAttribute("courseId", responseDto.getObject());

        Ctdt ctdt = ctdtRepository.findById(ctdtId).orElse(null);

        //get mon
        List<CtdtDTO> ctdtDTOS = null;
        if (ctdt.getMajorId() != null) {
            ResponseDto responseDtCtdt = ctdtService.findByMajorId(ctdt.getMajorId());
            ctdtDTOS = (List<CtdtDTO>) responseDtCtdt.getObject();
            model.addAttribute("ctdtId", ctdt.getMajorId());
        }
        model.addAttribute("dataCtdt", ctdtDTOS);


        return "fragment/body/home/edit/edit-course-class";
    }

    @PutMapping("updateCourseClass")
    public String updateCourseClass(
            @ModelAttribute CourseClassDTO courseClassDTO
    ) {
        ResponseDto responseDto = courseClassServcie.update(courseClassDTO);
        return "redirect:/quan-ly-lop-chung";
    }

    @DeleteMapping("deleteCourseClass")
    public String deleteCourseClass(
            @RequestParam(name = "id") Long id
    ) {
        ResponseDto responseDto = courseClassServcie.delete(id);
        return "redirect:/quan-ly-lop-chung";
    }
}
