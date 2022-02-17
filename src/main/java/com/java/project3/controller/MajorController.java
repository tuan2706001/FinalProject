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

    @GetMapping("quan-ly-nganh/{id}")
    public  String suaMon (
            @PathVariable("id") Long id,
            Model model
    ) {
        ResponseDto responseDto =  majorService.findById(id);
        model.addAttribute("data", responseDto.getObject());
        return "fragment/body/home/edit/edit-major";
    }

    @PutMapping("updateMajor")
    public String updateMajor(
            Model model,
            @ModelAttribute MajorDTO majorDTO
    ) {
        ResponseDto responseDto = majorService.update(majorDTO);
        model.addAttribute("updateMajor");
        return "redirect:/quan-ly-nganh";
    }

    @DeleteMapping("deleteMajor")
    public String deleteMajor(
            @RequestParam(name = "id") Long id
    ) {
        ResponseDto responseDto = majorService.delete(id);
        return "redirect:/quan-ly-nganh";
    }




}
