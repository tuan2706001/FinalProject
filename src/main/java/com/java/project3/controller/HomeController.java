package com.java.project3.controller;

import com.java.project3.dto.MajorDTO;
import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchResDto;
import com.java.project3.service.MajorService;
import com.java.project3.service.StudentService;
import com.java.project3.utils.PageUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    StudentService studentService;

    @GetMapping(value = {"/trang-chu"})
    public String home(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "gradeId", required = false) Long gradeId

    ) {
        if (gradeId == null || gradeId == 0) {
            gradeId = null;
        }
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = studentService.searchStudentBy(page.getCurrentPage() - 1, page.getPageSize(), search, gradeId);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);
        return "trang-chu";
    }

    @GetMapping("thong-ke")
    public String statistical() {
        return "thong-ke";
    }

    @GetMapping("thong-tin-ca-nhan")
    public String info() {
        return "thong-tin-ca-nhan";
    }

    @GetMapping(value = {"/","/dang-nhap"})
    public String login() {
        return "dang-nhap";
    }

    @GetMapping("dang-ky")
    public String signUp() {
        return "dang-ky";
    }




}
