package com.java.project3.controller;

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

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/")
public class MarkController {
    @Autowired
    MarkService markService;
    @Autowired
    CourseClassServcie gradeServcie;
    @Autowired
    StudentService studentService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    MajorService majorService;
    @Autowired
    CtdtSubjectClassService ctdtSubjectClassService;

    @GetMapping("quan-ly-diem")
    public String mark(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "ctdtSubjectClassId", required = false) Long ctdtSubjectClassId,
            @RequestParam(value = "classId", required = false) Long classId,
            @RequestParam(value = "subjectId", required = false) Long subjectId,
            @RequestParam(value = "studentId", required = false) Long studentId,
            @RequestParam(value = "status", required = false) Integer status,
            HttpServletResponse response
    ) {
        if (ctdtSubjectClassId == null || ctdtSubjectClassId == 0) {
            ctdtSubjectClassId = null;
        }
        if (subjectId == null || subjectId == 0) {
            subjectId = null;
        }

        Page page = new Page();
        page = PageUltil.setDefault(currentPage, 20);
        ResponseDto responseDto = markService.searchMarkBy(page.getCurrentPage() - 1, page.getPageSize(), search , ctdtSubjectClassId, status, subjectId);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), 20);
        model.addAttribute("page", page);
        model.addAttribute("search", search);
        model.addAttribute("status", status);


        //get lớp theo môn
        SearchReqDto searchReqDtoLop = new SearchReqDto();
        searchReqDtoLop.setPageSize(100);
        searchReqDtoLop.setPageIndex(0);
        ResponseDto responseDtolop = ctdtSubjectClassService.search(searchReqDtoLop);
        SearchResDto searchResDtoLop = (SearchResDto) responseDtolop.getObject();
        model.addAttribute("lop", searchResDtoLop.getData());
        model.addAttribute("courseClassIds", classId);


        //dùng ajax lấu môn theo lớp môn
        List<SubjectDTO> subjectDTOS = null;
        if (classId != null) {
            ResponseDto responseDto1 = subjectService.findById(subjectId);
            SubjectDTO subjectDTO = (SubjectDTO) responseDto1.getObject();
            model.addAttribute("courseClassIds", subjectDTO.getCtdtSubjectClassId());

            ResponseDto responseDto2 = subjectService.findByCscId(subjectDTO.getCtdtSubjectClassId());
            subjectDTOS = (List<SubjectDTO>) responseDto2.getObject();
            model.addAttribute("subjectId", subjectId);
        }
        model.addAttribute("dataSubject", subjectDTOS);

        //dùng ajax lấu sinh viên theo lớp môn
        List<StudentDTO> studentDTOS = null;
        if (classId != null) {
            ResponseDto responseDto1 = studentService.findById(studentId);
            StudentDTO studentDTO = (StudentDTO) responseDto1.getObject();
            model.addAttribute("classId", studentDTO.getCtdtSubjectClassId());

            ResponseDto responseDto2 = subjectService.findByCscId(studentDTO.getCtdtSubjectClassId());
            studentDTOS = (List<StudentDTO>) responseDto2.getObject();
            model.addAttribute("studentId", studentId);
        }
        model.addAttribute("dataStudent", studentDTOS);


        return "quan-ly-diem";
    }

    @GetMapping("getStudent/{id}")
    @ResponseBody
    public List<StudentDTO> getStudent(
            @PathVariable("id") Long id
    ) {
        ResponseDto responseDto = studentService.findByCscId(id);
        return (List<StudentDTO>) responseDto.getObject();
    }

    @GetMapping("getSubjectByClass/{id}")
    @ResponseBody
    public List<SubjectDTO> getSubjectByClass(
            @PathVariable("id") Long id
    ) {
        ResponseDto responseDto = subjectService.findByCscId(id);
        return (List<SubjectDTO>) responseDto.getObject();
    }

    @PostMapping("createMark")
    public String createMark(
            @ModelAttribute MarkDTO markDTO
    ) {
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
