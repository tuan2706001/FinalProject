package com.java.project3.controller;

import com.java.project3.domain.Subject;
import com.java.project3.domain.Teacher;
import com.java.project3.dto.CtdtDTO;
import com.java.project3.dto.SubjectDTO;
import com.java.project3.dto.TeacherDTO;
import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.dto.base.SearchResDto;
import com.java.project3.repository.SubjectRepository;
import com.java.project3.repository.TeacherRepository;
import com.java.project3.service.SubjectService;
import com.java.project3.service.TeacherService;
import com.java.project3.utils.PageUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("quan-ly-giao-vien")
    public String teacher(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "subjectId", required = false) Long subjectId
    ) {
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = teacherService.searchTeacherBy(page.getCurrentPage() - 1, page.getPageSize(), search);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);

        //get subject
        SearchReqDto searchReqDtoMon = new SearchReqDto();
        searchReqDtoMon.setPageSize(100);
        searchReqDtoMon.setPageIndex(0);
        ResponseDto responseDtoMon = subjectService.search(searchReqDtoMon);
        SearchResDto searchResDtoMon = (SearchResDto) responseDtoMon.getObject();
        model.addAttribute("mon", searchResDtoMon.getData());
        model.addAttribute("subjectId", subjectId);

//        List<Teacher> teachers = teacherRepository.findAll();
//        for (Teacher teacher : teachers) {
//            //get mon
//            List<SubjectDTO> subjectDTOS = null;
//            if (teacher.getId() != null) {
//                ResponseDto responseDtCtdt = subjectService.findByTeacherId(teacher.getId());
//                subjectDTOS = (List<SubjectDTO>) responseDtCtdt.getObject();
//                model.addAttribute("subjectIds", teacher.getId());
//            }
//            model.addAttribute("dataMon", subjectDTOS);
//        }


        return "quan-ly-giao-vien";
    }

    @PostMapping("createTeacher")
    public String createTeacher(
            @ModelAttribute TeacherDTO teacherDTO
    ) {
        ResponseDto responseDto = teacherService.create(teacherDTO);
        return "redirect:/quan-ly-giao-vien";
    }

    @GetMapping("quan-ly-giao-vien/{id}")
    public  String suaGiaoVien (
            @PathVariable("id") Long id,
            Model model
    ) {
        ResponseDto responseDto =  teacherService.findById(id);
        model.addAttribute("data", responseDto.getObject());

//        List<Subject> subjects = subjectRepository.findByTeacherId(id);
//
//        //get subject
//        SearchReqDto searchReqDtoMon = new SearchReqDto();
//        searchReqDtoMon.setPageSize(100);
//        searchReqDtoMon.setPageIndex(0);
//        ResponseDto responseDtoMon = subjectService.search(searchReqDtoMon);
//        SearchResDto searchResDtoMon = (SearchResDto) responseDtoMon.getObject();
//        model.addAttribute("mon", searchResDtoMon.getData());
//        model.addAttribute("subjectId", subjectId);

        //get mon
        List<SubjectDTO> subjectDTOS = null;
        if (id != null) {
            ResponseDto responseDtCtdt = subjectService.findByTeacherId(id);
            subjectDTOS = (List<SubjectDTO>) responseDtCtdt.getObject();
            model.addAttribute("subjectIds", id);
        }
        model.addAttribute("dataMon", subjectDTOS);
        return "fragment/body/home/edit/edit-teacher";
    }

    @PutMapping("updateTeacher")
    public String updateTeacher(
            @ModelAttribute TeacherDTO teacherDTO
    ) {
        ResponseDto responseDto = teacherService.update(teacherDTO);
        return "redirect:/quan-ly-giao-vien";
    }

    @DeleteMapping("deleteTeacher")
    public String deleteTeacher(
            @RequestParam(name = "id") Long id
    ) {
        ResponseDto responseDto = teacherService.delete(id);
        return "redirect:/quan-ly-giao-vien";
    }
}
