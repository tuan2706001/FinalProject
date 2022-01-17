package com.java.project3.controller;

import com.java.project3.dto.CourseDTO;
import com.java.project3.dto.MajorDTO;
import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchResDto;
import com.java.project3.service.CourseServcice;
import com.java.project3.utils.PageUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class CourseController {
    @Autowired
    CourseServcice courseServcice;

    @GetMapping("quan-ly-khoa")
    public String major(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search
    ) {
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = courseServcice.searchCourseBy(page.getCurrentPage() - 1, page.getPageSize(), search);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);

        return "quan-ly-khoa";
    }

    @PostMapping("createCourse")
    public String createCourse(
            @ModelAttribute CourseDTO courseDTO
    ) {
        ResponseDto responseDto = courseServcice.create(courseDTO);
        return "redirect:/quan-ly-khoa";
    }

    @PostMapping("updateCourse")
    public String updateCourse(
            @ModelAttribute CourseDTO courseDTO
    ) {
        ResponseDto responseDto = courseServcice.update(courseDTO);
        return "redirect:/quan-ly-khoa";
    }
}
