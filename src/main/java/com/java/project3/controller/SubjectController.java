package com.java.project3.controller;

import com.java.project3.domain.Ctdt;
import com.java.project3.dto.CtdtDTO;
import com.java.project3.dto.CtdtSubjectDTO;
import com.java.project3.dto.SubjectDTO;
import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.dto.base.SearchResDto;
import com.java.project3.repository.CtdtRepository;
import com.java.project3.service.*;
import com.java.project3.utils.PageUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/")
public class SubjectController {
    @Autowired
    SubjectService subjectService;

    @GetMapping("quan-ly-mon-hoc")
    public String major(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "search", required = false) String search
    ) {
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = subjectService.searchSubjectBy(page.getCurrentPage() - 1, page.getPageSize(), search, null);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);

        return "quan-ly-mon-hoc";
    }


    @PostMapping("createSubject")
    public String createSubject(
            @ModelAttribute SubjectDTO subjectDTO
    ) {
        ResponseDto responseDto = subjectService.create(subjectDTO);
        return "redirect:/quan-ly-mon-hoc";
    }

    @GetMapping("quan-ly-mon-hoc/{id}")
    public  String suaMon (
            @PathVariable("id") Long id,
            Model model
    ) {
        ResponseDto responseDto =  subjectService.findById(id);
        model.addAttribute("data", responseDto.getObject());
        return "fragment/body/home/edit/edit-subject";
    }

    @PutMapping("updateSubject")
    public String updateSubject(
            @ModelAttribute SubjectDTO subjectDTO
    ) {
        ResponseDto responseDto = subjectService.update(subjectDTO);
        return "redirect:/quan-ly-mon-hoc";
    }

    @DeleteMapping("deleteSubject")
    public String deleteSubject(
            @RequestParam(name = "id") Long id
    ) {
        ResponseDto responseDto = subjectService.delete(id);
        return "redirect:/quan-ly-mon-hoc";
    }

}
