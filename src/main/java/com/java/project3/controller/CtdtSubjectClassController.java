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
public class CtdtSubjectClassController {
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

    @GetMapping("quan-ly-lop-theo-mon")
    public String ctdtSubjectClass(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "courseClassId", required = false) Long courseClassId,
            @RequestParam(value = "majorId", required = false) Long majorId,
            @RequestParam(value = "ctdtId", required = false) Long ctdtId,
            @RequestParam(value = "ctdtSubjectId", required = false) Long ctdtSubjectId
    ) {
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = ctdtSubjectClassService.searchCtdtSubjectClassBy(page.getCurrentPage() - 1, page.getPageSize(), search, courseClassId);
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
            model.addAttribute("ctdtIds", ctdtId);
        }
        model.addAttribute("dataCtdt", ctdtDTOS);

        //dùng ajax lấy lớp chung theo ctdt
        List<CourseClassDTO> courseClassDTOS = null;
        if (ctdtId != null) {
            ResponseDto responseDto1 = courseClassServcie.findById(courseClassId);
            CourseClassDTO courseClassDTO = (CourseClassDTO) responseDto1.getObject();
            model.addAttribute("ctdtIds", courseClassDTO.getCtdtId());

            ResponseDto responseDto2 = courseClassServcie.findByCtdtId(courseClassDTO.getCtdtId());
            courseClassDTOS = (List<CourseClassDTO>) responseDto2.getObject();
            model.addAttribute("courseClassIds", courseClassId);
        }
        model.addAttribute("dataCourseClass", courseClassDTOS);

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



        return "quan-ly-lop-theo-mon";
    }

    @GetMapping("getCourseClass/{id}")
    @ResponseBody
    public List<CourseClassDTO> getCourseClass(
            @PathVariable("id") Long id
    ) {
        ResponseDto responseDto = courseClassServcie.findByCtdtId(id);
        return (List<CourseClassDTO>) responseDto.getObject();
    }

    @GetMapping("getSubject/{id}")
    @ResponseBody
    public List<CtdtSubjectDTO> getSubject(
            @PathVariable("id") Long id
    ) {
        ResponseDto responseDto = ctdtSubjectService.findByCtdtId(id);
        return (List<CtdtSubjectDTO>) responseDto.getObject();
    }

    @GetMapping("getTeacher/{id}")
    @ResponseBody
    public List<TeacherSubjectDTO> getTeacher(
            @PathVariable("id") Long id
    ) {
        ResponseDto responseDto = teacherService.findByCtdtSubjectId(id);
        return (List<TeacherSubjectDTO>) responseDto.getObject();
    }

    @PostMapping("createCtdtSubjectClass")
    public String createCtdtSubjectClass(
            @ModelAttribute CtdtSubjectClassDTO ctdtSubjectClassDTO
    ) {
        ResponseDto responseDto = ctdtSubjectClassService.create(ctdtSubjectClassDTO);
        return "redirect:/quan-ly-lop-theo-mon";
    }

    @GetMapping("quan-ly-lop-theo-mon/{id}/{ctdtSubjectId}")
    public  String suaCtdtSubejctClass (
            @PathVariable("id") Long id,
            @PathVariable("ctdtSubjectId") Long ctdtSubjectId,
            Model model
    ) {
        ResponseDto responseDto =  ctdtSubjectClassService.findById(id);
        model.addAttribute("data", responseDto.getObject());

//        //dùng ajax lấy môn theo ctdt
//        List<CtdtSubjectDTO> ctdtSubjectDTOS = null;
//        if (ctdtId != null) {
//            ResponseDto responseDto1 = ctdtService.findById(ctdtId);
//            CtdtSubjectDTO ctdtSubjectDTO = (CtdtSubjectDTO) responseDto1.getObject();
//            model.addAttribute("ctdtIds", ctdtSubjectDTO.getCtdtId());
//
//            ResponseDto responseDto2 = ctdtSubjectService.findByCtdtId(ctdtSubjectDTO.getCtdtId());
//            ctdtSubjectDTOS = (List<CtdtSubjectDTO>) responseDto2.getObject();
//            model.addAttribute("subjectIds", ctdtId);
//        }
//        model.addAttribute("dataSubject", ctdtSubjectDTOS);
        //dùng ajax lấy giáo viên theo subject
//        List<TeacherSubjectDTO> teacherSubjectDTOS = null;
//        if (ctdtSubjectId != null) {
//            ResponseDto responseDto1 = ctdtSubjectService.findById(ctdtSubjectId);
//            TeacherSubjectDTO teacherSubjectDTO = (TeacherSubjectDTO) responseDto1.getObject();
////            model.addAttribute("ctdtSubjectIds", teacherSubjectDTO.getCtdtSubjectId());
//
//            ResponseDto responseDto2 = teacherService.findByCtdtSubjectId(teacherSubjectDTO.getCtdtSubjectId());
//            teacherSubjectDTOS = (List<TeacherSubjectDTO>) responseDto2.getObject();
//            model.addAttribute("teacherId", ctdtSubjectId);
//        }
//        model.addAttribute("dataTeacher", teacherSubjectDTOS);

        //get mon
        List<TeacherDTO> teacherDTOS = null;
        if (ctdtSubjectId != null) {
            ResponseDto responseDtSubject = teacherService.findTeacherByCtdtSubjectId(ctdtSubjectId);
            teacherDTOS = (List<TeacherDTO>) responseDtSubject.getObject();
            model.addAttribute("teacherId", ctdtSubjectId);
        }
        model.addAttribute("dataTeacher", teacherDTOS);

//        //dùng ajax lấu ngành theo khóa
//        List<CtdtDTO> ctdtDTOS = null;
//        if (majorId != null) {
//            ResponseDto responseDto1 = ctdtService.findById(ctdtId);
//            CtdtDTO ctdtDTO = (CtdtDTO) responseDto1.getObject();
//            model.addAttribute("majorId", ctdtDTO.getMajorId());
//
//            ResponseDto responseDto2 = ctdtService.findByMajorId(ctdtDTO.getMajorId());
//            ctdtDTOS = (List<CtdtDTO>) responseDto2.getObject();
//            model.addAttribute("subjectIds", ctdtId);
//
//        }
//        model.addAttribute("dataCtdt", ctdtDTOS);

        return "fragment/body/home/edit/edit-ctdt-subject-class";
    }

    @PutMapping("updateCtdtSubjectClass")
    public String updateCtdtSubjectClass(
            @ModelAttribute CtdtSubjectClassDTO ctdtSubjectClassDTO
    ) {
        ResponseDto responseDto = ctdtSubjectClassService.update(ctdtSubjectClassDTO);
        return "redirect:/quan-ly-lop-theo-mon";
    }

    @DeleteMapping("deleteCtdtSubjectClass")
    public String deleteCtdtSubjectClass(
            @RequestParam(name = "id") Long id
    ) {
        ResponseDto responseDto = ctdtSubjectClassService.delete(id);
        return "redirect:/quan-ly-lop-theo-mon";
    }
}
