package com.java.project3.controller;

import com.java.project3.dto.MajorDTO;
import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchResDto;
import com.java.project3.service.MajorService;
import com.java.project3.utils.PageUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MajorController {
    @Autowired
    MajorService majorService;

    @GetMapping("quan-ly-nganh")
    public String major(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search
    ) {
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = majorService.searchMajorBy(page.getCurrentPage() - 1, page.getPageSize(), search);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);

        return "quan-ly-nganh";
    }

    @PostMapping("createMajor")
    public String createMajor(
            @ModelAttribute MajorDTO majorDTO
    ) {
        ResponseDto responseDto = majorService.create(majorDTO);
        return "redirect:/quan-ly-nganh";
    }

    @PostMapping("updateMajor")
    public String updateMajor(
            @ModelAttribute MajorDTO majorDTO
    ) {
        ResponseDto responseDto = majorService.update(majorDTO);
        return "redirect:/quan-ly-nganh";
    }




}
