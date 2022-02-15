package com.java.project3.controller;

import com.java.project3.dto.UserDTO;
import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchResDto;
import com.java.project3.service.MinistryService;
import com.java.project3.utils.PageUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MinistryController {
    @Autowired
    MinistryService ministryService;

    @GetMapping("quan-ly-giao-vu")
    public String ministry(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search
    ) {
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = ministryService.searchMajorBy(page.getCurrentPage() - 1, page.getPageSize(), search);
        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
        model.addAttribute("findAll", searchResDto.getData());
        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
        model.addAttribute("page", page);
        model.addAttribute("search", search);

        return "quan-ly-giao-vu";
    }

    @PostMapping("createMinistry")
    public String createMinistry(
            @ModelAttribute UserDTO userDTO
    ) {
        ResponseDto responseDto = ministryService.create(userDTO);
        return "redirect:/quan-ly-giao-vu";
    }

    @PostMapping("updateMinistry")
    public String updateMinistry(
            Model model,
            @ModelAttribute UserDTO userDTO
    ) {
        ResponseDto responseDto = ministryService.update(userDTO);
        model.addAttribute("updateMinistry");
        return "redirect:/quan-ly-giao-vu";
    }
}
