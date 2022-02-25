package com.java.project3.controller;

import com.java.project3.domain.Grade;
import com.java.project3.domain.Mark;
import com.java.project3.dto.*;
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
    @Autowired
    MajorService majorService;

    @GetMapping("quan-ly-diem")
    public String mark(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "gradeId", required = false) Long gradeId,
            @RequestParam(value = "studentId", required = false) Long studentId,
            @RequestParam(value = "subjectId", required = false) Long subjectId
    ) {
        if (gradeId == null || gradeId == 0) {
            gradeId = null;
        }
        if (studentId == null || studentId == 0) {
            studentId = null;
        }
        if (subjectId == null || subjectId == 0) {
            subjectId = null;
        }

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
        model.addAttribute("gradeIdss", gradeId);

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


        //dùng ajax lọc danh sách môn học theo lớp
        List<SubjectDTO> subjectDTOS = null;
        if (subjectId != null) {
            ResponseDto subject = subjectService.findById(subjectId);
            SubjectDTO subjectDTO = (SubjectDTO) subject.getObject();

            ResponseDto mark = markService.findBySubjectId(subjectDTO.getId());
            List<MarkDTO> markDTO = (List<MarkDTO>) mark.getObject();


//            ResponseDto grade = gradeServcie.findByMarkId(((MarkDTO) mark.getObject()).getGradeId());
//            List<GradeDTO> gradeDTO = (List<GradeDTO>) grade.getObject();
//            model.addAttribute("gradeIdss", gradeDTO.getId());
//
//            ResponseDto responseDto2 = subjectService.findByGradeId(gradeDTO.getId());
//            subjectDTOS = (List<SubjectDTO>) responseDto2.getObject();
//            model.addAttribute("subjectIds", subjectId);

        }
        model.addAttribute("dataMons", subjectDTOS);


        return "quan-ly-diem";
    }

    @GetMapping("getStudent/{id}")
    @ResponseBody
    public List<StudentDTO> getStudent(
            @PathVariable("id") Long id
    ) {
        ResponseDto responseDto = studentService.findByGradeId(id);
        return (List<StudentDTO>) responseDto.getObject();
    }

    @GetMapping("getSubject/{id}")
    @ResponseBody
    public List<SubjectDTO> getSubject(
            @PathVariable("id") Long id
    ) {
        ResponseDto responseDto = subjectService.findByGradeId(id);
        return (List<SubjectDTO>) responseDto.getObject();
    }

    @PostMapping("createMark")
    public String createMark(
            Model model,
            @ModelAttribute MarkDTO markDTO
    ) {

        //dùng ajax lọc dánh sách sinh viên
        List<StudentDTO> studentDTOS = null;
        if (markDTO.getStudentId() != null) {
            ResponseDto responseDto1 = studentService.findById(markDTO.getStudentId());
            StudentDTO studentDTO = (StudentDTO) responseDto1.getObject();
            model.addAttribute("gradeId", studentDTO.getGradeId());

            //get lớp
            SearchReqDto searchReqDtoLop = new SearchReqDto();
            ResponseDto responseDtolop = gradeServcie.search(searchReqDtoLop);
            SearchResDto searchResDtoLop = (SearchResDto) responseDtolop.getObject();
            model.addAttribute("lop", searchResDtoLop.getData());
            model.addAttribute("gradeIds", markDTO.getGradeId());

            ResponseDto responseDto2 = studentService.findByGradeId(studentDTO.getGradeId());
            studentDTOS = (List<StudentDTO>) responseDto2.getObject();
            model.addAttribute("studentIdss", markDTO.getStudentId());

        }
        model.addAttribute("dataSv", studentDTOS);

        //dùng ajax lọc dánh sách môn học
        List<SubjectDTO> subjectDTOS = null;
        if (markDTO.getSubjectId() != null) {
            ResponseDto responseDto1 = studentService.findById(markDTO.getStudentId());
            StudentDTO studentDTO = (StudentDTO) responseDto1.getObject();
            model.addAttribute("gradeId", studentDTO.getGradeId());

            //get lớp
            SearchReqDto searchReqDtoLop = new SearchReqDto();
            ResponseDto responseDtolop = gradeServcie.search(searchReqDtoLop);
            SearchResDto searchResDtoLop = (SearchResDto) responseDtolop.getObject();
            model.addAttribute("lop", searchResDtoLop.getData());
            model.addAttribute("gradeIdss", markDTO.getGradeId());

            ResponseDto responseDto2 = subjectService.findByGradeId(studentDTO.getGradeId());
            subjectDTOS = (List<SubjectDTO>) responseDto2.getObject();
            model.addAttribute("subjectIdss", markDTO.getSubjectId());

        }
        model.addAttribute("dataMon", subjectDTOS);

        ResponseDto responseDto = markService.create(markDTO);
        return "redirect:/quan-ly-diem";
    }

    @GetMapping("/quan-ly-diem/{id}")
    public String suaDien(
            @PathVariable("id") Long id,
            Model model
    ) {
        ResponseDto responseDto = markService.findById(id);
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
