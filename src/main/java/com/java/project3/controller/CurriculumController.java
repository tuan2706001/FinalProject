package com.java.project3.controller;

import com.java.project3.dto.CurriculumDTO;
import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchResDto;
import com.java.project3.service.CurriculumService;
import com.java.project3.utils.PageUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class CurriculumController {
    @Autowired
    CurriculumService curriculumService;

    @GetMapping("quan-ly-he")
    public String curriculum(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search
    ) {
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = curriculumService.searchCurriculumBy(page.getCurrentPage() - 1, page.getPageSize(), search);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);

        return "quan-ly-he";
    }

    @PostMapping("createCurriculum")
    public String createCurriculum(
            @ModelAttribute CurriculumDTO curriculumDTO
    ) {
        ResponseDto responseDto = curriculumService.create(curriculumDTO);
        return "redirect:/quan-ly-he";
    }

    @GetMapping("quan-ly-he/{id}")
    public  String suaHe (
            @PathVariable("id") Long id,
            Model model
    ) {
        ResponseDto responseDto =  curriculumService.findById(id);
        model.addAttribute("data", responseDto.getObject());
        return "fragment/body/home/edit/edit-curriculum";
    }

    @PutMapping("updateCurriculum")
    public String updateCurriculum(
            @ModelAttribute CurriculumDTO curriculumDTO
    ) {
        ResponseDto responseDto = curriculumService.update(curriculumDTO);
        return "redirect:/quan-ly-he";
    }

    @DeleteMapping("deleteCurriculum")
    public String deleteCurriculum(
            @RequestParam(name = "id") Long id
    ) {
        ResponseDto responseDto = curriculumService.delete(id);
        return "redirect:/quan-ly-he";
    }
}
