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
            @RequestParam(value = "classId", required = false) Long classId,
            @RequestParam(value = "subjectId", required = false) Long subjectId,
            @RequestParam(value = "studentId", required = false) Long studentId,
            @RequestParam(value = "status", required = false) Short status,
            HttpServletResponse response
    ) {
        if (classId == null || classId == 0) {
            classId = null;
        }
        if (subjectId == null || subjectId == 0) {
            subjectId = null;
        }

        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = markService.searchMarkBy(page.getCurrentPage() - 1, page.getPageSize(), search , classId, status, subjectId);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);
        model.addAttribute("status", status);
//        model.addAttribute("gradeId", gradeId);
//        model.addAttribute("subjectId", subjectId);


        //get lớp
        SearchReqDto searchReqDtoLop = new SearchReqDto();
        searchReqDtoLop.setPageSize(100);
        searchReqDtoLop.setPageIndex(0);
        ResponseDto responseDtolop = ctdtSubjectClassService.search(searchReqDtoLop);
        SearchResDto searchResDtoLop = (SearchResDto) responseDtolop.getObject();
        model.addAttribute("lop", searchResDtoLop.getData());
        model.addAttribute("classId", classId);



        //dùng ajax lấu ngành theo khóa
        List<SubjectDTO> subjectDTOS = null;
        if (classId != null) {
            ResponseDto responseDto1 = subjectService.findById(subjectId);
            SubjectDTO subjectDTO = (SubjectDTO) responseDto1.getObject();
            model.addAttribute("classId", subjectDTO.getCtdtSubjectClassId());

            ResponseDto responseDto2 = subjectService.findByCscId(subjectDTO.getCtdtSubjectClassId());
            subjectDTOS = (List<SubjectDTO>) responseDto2.getObject();
            model.addAttribute("subjectId", subjectId);
        }
        model.addAttribute("dataSubject", subjectDTOS);

        //dùng ajax lấu ngành theo khóa
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
//
//    @PostMapping("createMark")
//    public String createMark(
//            Model model,
//            @ModelAttribute MarkDTO markDTO
//    ) {
//
//        //dùng ajax lọc dánh sách sinh viên
//        List<StudentDTO> studentDTOS = null;
//        if (markDTO.getStudentId() != null) {
//            ResponseDto responseDto1 = studentService.findById(markDTO.getStudentId());
//            StudentDTO studentDTO = (StudentDTO) responseDto1.getObject();
//            model.addAttribute("gradeId", studentDTO.getGradeId());
//
//            //get lớp
//            SearchReqDto searchReqDtoLop = new SearchReqDto();
//            ResponseDto responseDtolop = gradeServcie.search(searchReqDtoLop);
//            SearchResDto searchResDtoLop = (SearchResDto) responseDtolop.getObject();
//            model.addAttribute("lop", searchResDtoLop.getData());
//            model.addAttribute("gradeIds", markDTO.getGradeId());
//
//            ResponseDto responseDto2 = studentService.findByGradeId(studentDTO.getGradeId());
//            studentDTOS = (List<StudentDTO>) responseDto2.getObject();
//            model.addAttribute("studentIdss", markDTO.getStudentId());
//
//        }
//        model.addAttribute("dataSv", studentDTOS);
//
//        //dùng ajax lọc dánh sách môn học
//        List<SubjectDTO> subjectDTOS = null;
//        if (markDTO.getSubjectId() != null) {
//            ResponseDto responseDto1 = studentService.findById(markDTO.getStudentId());
//            StudentDTO studentDTO = (StudentDTO) responseDto1.getObject();
//            model.addAttribute("gradeId", studentDTO.getGradeId());
//
//            //get lớp
//            SearchReqDto searchReqDtoLop = new SearchReqDto();
//            ResponseDto responseDtolop = gradeServcie.search(searchReqDtoLop);
//            SearchResDto searchResDtoLop = (SearchResDto) responseDtolop.getObject();
//            model.addAttribute("lop", searchResDtoLop.getData());
//            model.addAttribute("gradeIdss", markDTO.getGradeId());
//
//            ResponseDto responseDto2 = subjectService.findByGradeId(studentDTO.getGradeId());
//            subjectDTOS = (List<SubjectDTO>) responseDto2.getObject();
//            model.addAttribute("subjectIdss", markDTO.getSubjectId());
//
//        }
//        model.addAttribute("dataMon", subjectDTOS);
//
//        ResponseDto responseDto = markService.create(markDTO);
//        return "redirect:/quan-ly-diem";
//    }

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
