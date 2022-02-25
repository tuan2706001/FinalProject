package com.java.project3.controller;

import com.java.project3.dto.UserDTO;
import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
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

    @GetMapping("/quan-ly-giao-vu/{id}")
    public  String suaGiaoVu (
            @PathVariable("id") Long id,
            Model model
    ) {
        ResponseDto responseDto =  ministryService.findById(id);
        model.addAttribute("data", responseDto.getObject());
        return "fragment/body/home/edit/edit-ministry";
    }

    @PutMapping("updateMinistry")
    public String updateMinistry(
            @ModelAttribute UserDTO userDTO
    ) {
        ResponseDto responseDto = ministryService.update(userDTO);
        return "redirect:/quan-ly-giao-vu";
    }

    @DeleteMapping("deleteMinistry")
    public String deleteMinistry(
            @RequestParam(name = "id") Long id
    ) {
        ResponseDto responseDto = ministryService.delete(id);
        return "redirect:/quan-ly-giao-vu";
    }

//    @GetMapping("thong-tin-ca-nhan")
//    public String info() {
//        return "thong-tin-ca-nhan";
//    }

    @GetMapping("/thong-tin-ca-nhan/{id}")
    public  String suaInfo (
            @PathVariable("id") Long id,
            Model model
    ) {
        ResponseDto responseDto =  ministryService.findById(id);
        model.addAttribute("data", responseDto.getObject());
        return "fragment/body/home/thong-tin-ca-nhan";
    }

    @PutMapping("updateInfo")
    public String updateInfo(
            @ModelAttribute UserDTO userDTO
    ) {
        ResponseDto responseDto = ministryService.update(userDTO);
        return "redirect:/thong-tin-ca-nhan";
    }
}
