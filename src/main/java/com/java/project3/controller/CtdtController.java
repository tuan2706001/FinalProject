package com.java.project3.controller;

import com.java.project3.dto.CtdtDTO;
import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.dto.base.SearchResDto;
import com.java.project3.service.CourseServcice;
import com.java.project3.service.CtdtService;
import com.java.project3.service.MajorService;
import com.java.project3.utils.PageUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class CtdtController {
    @Autowired
    MajorService majorService;
    @Autowired
    CourseServcice courseServcice;
    @Autowired
    CtdtService ctdtService;

    @GetMapping("quan-ly-ctdt")
    public String ctdt(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "majorId", required = false) Long majorId
    ) {
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = ctdtService.searchCtdtBy(page.getCurrentPage() - 1, page.getPageSize(), search, majorId);
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

        return "quan-ly-ctdt";
    }

    @PostMapping("createCtdt")
    public String createCtdt(
            @ModelAttribute CtdtDTO ctdtDTO
    ) {
        ResponseDto responseDto = ctdtService.create(ctdtDTO);
        return "redirect:/quan-ly-ctdt";
    }

    @GetMapping("quan-ly-ctdt/{id}")
    public  String suaCtdt (
            @PathVariable("id") Long id,
            Model model
    ) {

        //get nganh
        SearchReqDto searchReqDtoNganh = new SearchReqDto();
        searchReqDtoNganh.setPageSize(100);
        searchReqDtoNganh.setPageIndex(0);
        ResponseDto responseDtNganh = majorService.search(searchReqDtoNganh);
        SearchResDto searchResDtoNganh = (SearchResDto) responseDtNganh.getObject();
        model.addAttribute("nganhs", searchResDtoNganh.getData());
        ResponseDto responseDto =  ctdtService.findById(id);
        model.addAttribute("data", responseDto.getObject());
        return "fragment/body/home/edit/edit-ctdt";
    }

    @PutMapping("updateCtdt")
    public String updateCtdt(
            Model model,
            @ModelAttribute CtdtDTO ctdtDTO
    ) {
        ResponseDto responseDto = ctdtService.update(ctdtDTO);
        model.addAttribute("updateCtdt");
        return "redirect:/quan-ly-ctdt";
    }

    @DeleteMapping("deleteCtdt")
    public String deleteCtdt(
            @RequestParam(name = "id") Long id
    ) {
        ResponseDto responseDto = ctdtService.delete(id);
        return "redirect:/quan-ly-ctdt";
    }
}
