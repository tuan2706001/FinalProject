package com.java.project3.controller;

import com.java.project3.domain.CtdtSubject;
import com.java.project3.dto.CtdtDTO;
import com.java.project3.dto.CtdtSubjectDTO;
import com.java.project3.dto.SubjectDTO;
import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.dto.base.SearchResDto;
import com.java.project3.service.CtdtService;
import com.java.project3.service.CtdtSubjectService;
import com.java.project3.service.MajorService;
import com.java.project3.service.SubjectService;
import com.java.project3.utils.PageUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class CtdtSubjectController {
    @Autowired
    CtdtSubjectService ctdtSubjectService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    CtdtService ctdtService;
    @Autowired
    MajorService majorService;

    @GetMapping("quan-ly-mon-hoc-chuyen-nganh")
    public String major(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "subjectId", required = false) Long subjectId,
            @RequestParam(value = "ctdtId", required = false) Long ctdtId,
            @RequestParam(value = "majorId", required = false) Long majorId
    ) {
        Page page = new Page();
        page = PageUltil.setDefault(currentPage, pageSize);
        ResponseDto responseDto = ctdtSubjectService.searchCtdtSubjectBy(page.getCurrentPage() - 1, page.getPageSize(), search, null);
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

        //get nganh
        SearchReqDto searchReqDtoNganh = new SearchReqDto();
        searchReqDtoNganh.setPageSize(100);
        searchReqDtoNganh.setPageIndex(0);
        ResponseDto responseDtoNganh = majorService.search(searchReqDtoNganh);
        SearchResDto searchResDtoNganh = (SearchResDto) responseDtoNganh.getObject();
        model.addAttribute("nganh", searchResDtoNganh.getData());
        model.addAttribute("majorId", majorId);

        //dùng ajax lấu ngành theo khóa
        List<CtdtDTO> ctdtDTOS = null;
        if (majorId != null) {
            ResponseDto responseDto1 = ctdtService.findById(ctdtId);
            CtdtDTO ctdtDTO = (CtdtDTO) responseDto1.getObject();
            model.addAttribute("majorId", ctdtDTO.getMajorId());

            ResponseDto responseDto2 = ctdtService.findByMajorId(ctdtDTO.getMajorId());
            ctdtDTOS = (List<CtdtDTO>) responseDto2.getObject();
            model.addAttribute("ctdtIds", ctdtId);

        }
        model.addAttribute("dataCtdt", ctdtDTOS);

        return "quan-ly-mon-hoc-chuyen-nganh";
    }

    @GetMapping("getCtdt/{id}")
    @ResponseBody
    public List<CtdtDTO> getCtdt(
            @PathVariable("id") Long id
    ) {
        ResponseDto responseDto = ctdtService.findByMajorId(id);
        return (List<CtdtDTO>) responseDto.getObject();
    }

    @PostMapping("createCtdtSubject")
    public String createCtdtSubject(
            @ModelAttribute CtdtSubjectDTO ctdtSubjectDTO
    ) {
        ResponseDto responseDto = ctdtSubjectService.create(ctdtSubjectDTO);
        return "redirect:/quan-ly-mon-hoc-chuyen-nganh";
    }

    @PutMapping("updateCtdtSubject")
    public String updateCtdtSubject(
            @ModelAttribute CtdtSubjectDTO ctdtSubjectDTO
    ) {
        ResponseDto responseDto = ctdtSubjectService.update(ctdtSubjectDTO);
        return "redirect:/quan-ly-mon-hoc-chuyen-nganh";
    }

    @DeleteMapping("deleteCtdtSubject")
    public String deleteSubjectDetail(
            @RequestParam(name = "id") Long id
    ) {
        ResponseDto responseDto = ctdtSubjectService.delete(id);
        return "redirect:/quan-ly-mon-hoc-chuyen-nganh";
    }
}
