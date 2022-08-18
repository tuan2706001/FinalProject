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

import java.util.List;

@Controller
@RequestMapping("/")
public class ClassRetestController {
    @Autowired
    CtdtSubjectClassService ctdtSubjectClassService;
    @Autowired
    CourseClassServcie courseClassServcie;
    @Autowired
    TeacherService teacherService;
    @Autowired
    MajorService majorService;
    @Autowired
    CtdtService ctdtService;
    @Autowired
    CtdtSubjectService ctdtSubjectService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    StudentService studentService;

    @GetMapping("quan-ly-lop-hoc-lai")
    public String classRetest(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "majorId", required = false) Long majorId,
            @RequestParam(value = "courseClassId", required = false) Long courseClassId,
            @RequestParam(value = "ctdtId", required = false) Long ctdtId,
            @RequestParam(value = "ctdtSubjectId", required = false) Long ctdtSubjectId
    ) {
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = ctdtSubjectClassService.searchClassRetestBy(page.getCurrentPage() - 1, page.getPageSize(), search);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);

        //get nganh
        SearchReqDto searchReqDtoNganh = new SearchReqDto();
        searchReqDtoNganh.setPageSize(100);
        searchReqDtoNganh.setPageIndex(0);
        ResponseDto responseDtoNganh = majorService.search(searchReqDtoNganh);
        SearchResDto searchResDtoNganh = (SearchResDto) responseDtoNganh.getObject();
        model.addAttribute("nganh", searchResDtoNganh.getData());
        model.addAttribute("majorId", majorId);

        //dùng ajax lấu ctdt theo ngành
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

        //dùng ajax lấy sinh viên theo ctdt
        List<StudentDTO> studentDTOS = null;
        if (ctdtId != null) {
            ResponseDto responseDto1 = ctdtService.findById(ctdtId);
            StudentDTO studentDTO = (StudentDTO) responseDto1.getObject();
            model.addAttribute("ctdtId", studentDTO.getCourseClassId());

            ResponseDto responseDto2 = studentService.findByCtdtId(ctdtId);
            studentDTOS = (List<StudentDTO>) responseDto2.getObject();
            model.addAttribute("studentIds", ctdtId);
        }
        model.addAttribute("dataStudent", studentDTOS);

        //get student
//        if (ctdtId != null) {
//            ResponseDto responseDtoMon = studentService.findByCtdtId(ctdtId);
//            SearchResDto searchResDtoMon = (SearchResDto) responseDtoMon.getObject();
//            model.addAttribute("student", searchResDtoMon.getData());
//            model.addAttribute("studentIds", ctdtId);
//        }

        //dùng ajax lấy môn theo ctdt
        List<CtdtSubjectDTO> ctdtSubjectDTOS = null;
        if (ctdtId != null) {
            ResponseDto responseDto1 = ctdtService.findById(ctdtId);
            CtdtSubjectDTO ctdtSubjectDTO = (CtdtSubjectDTO) responseDto1.getObject();
            model.addAttribute("ctdtIds", ctdtSubjectDTO.getCtdtId());

            ResponseDto responseDto2 = ctdtSubjectService.findByCtdtId(ctdtSubjectDTO.getCtdtId());
            ctdtSubjectDTOS = (List<CtdtSubjectDTO>) responseDto2.getObject();
            model.addAttribute("subjectIds", ctdtId);
        }
        model.addAttribute("dataSubject", ctdtSubjectDTOS);

        //dùng ajax lấy giáo viên theo subject
        List<TeacherSubjectDTO> teacherSubjectDTOS = null;
        if (ctdtSubjectId != null) {
            ResponseDto responseDto1 = ctdtSubjectService.findById(ctdtSubjectId);
            TeacherSubjectDTO teacherSubjectDTO = (TeacherSubjectDTO) responseDto1.getObject();
            model.addAttribute("ctdtSubjectIds", teacherSubjectDTO.getCtdtSubjectId());

            ResponseDto responseDto2 = teacherService.findByCtdtSubjectId(teacherSubjectDTO.getCtdtSubjectId());
            teacherSubjectDTOS = (List<TeacherSubjectDTO>) responseDto2.getObject();
            model.addAttribute("teacherIds", ctdtSubjectId);
        }
        model.addAttribute("dataTeacher", teacherSubjectDTOS);

        return "quan-ly-lop-hoc-lai";
    }

    @GetMapping("getStudentRetest/{id}")
    @ResponseBody
    public List<StudentDTO> getStudentRetest(
            @PathVariable("id") Long id
    ) {
        ResponseDto responseDto = studentService.findByCtdtId(id);
        return (List<StudentDTO>) responseDto.getObject();
    }


    @PostMapping("createClassRetest")
    public String createCtdtSubjectClass(
            @ModelAttribute CtdtSubjectClassDTO ctdtSubjectClassDTO
    ) {
        ResponseDto responseDto = ctdtSubjectClassService.createClassRetest(ctdtSubjectClassDTO);
        return "redirect:/quan-ly-lop-hoc-lai";
    }

    @GetMapping("quan-ly-lop-hoc-lai/{id}/{ctdtSubjectId}")
    public  String suaCtdtSubejctClass (
            @PathVariable("id") Long id,
            @PathVariable("ctdtSubjectId") Long ctdtSubjectId,
            Model model
    ) {
        ResponseDto responseDto =  ctdtSubjectClassService.findById(id);
        model.addAttribute("data", responseDto.getObject());

        //get mon
        List<TeacherDTO> teacherDTOS = null;
        if (ctdtSubjectId != null) {
            ResponseDto responseDtSubject = teacherService.findTeacherByCtdtSubjectId(ctdtSubjectId);
            teacherDTOS = (List<TeacherDTO>) responseDtSubject.getObject();
            model.addAttribute("teacherId", ctdtSubjectId);
        }
        model.addAttribute("dataTeacher", teacherDTOS);

        return "fragment/body/home/edit/edit-ctdt-subject-class";
    }

    @PutMapping("updateClassRetest")
    public String updateCtdtSubjectClass(
            @ModelAttribute CtdtSubjectClassDTO ctdtSubjectClassDTO
    ) {
        ResponseDto responseDto = ctdtSubjectClassService.update(ctdtSubjectClassDTO);
        return "redirect:/quan-ly-lop-hoc-lai";
    }

//    @PutMapping("updateStatus/{id}")
//    public String updateStatus(
//            @PathVariable("id") Long id
//    ) {
//        ResponseDto responseDto = ctdtSubjectClassService.updateTrangThaiThi(id);
//        return "redirect:/quan-ly-lop-hoc-lai";
//    }

    @DeleteMapping("deleteClassRetest")
    public String deleteCtdtSubjectClass(
            @RequestParam(name = "id") Long id
    ) {
        ResponseDto responseDto = ctdtSubjectClassService.delete(id);
        return "redirect:/quan-ly-lop-hoc-lai";
    }

}
