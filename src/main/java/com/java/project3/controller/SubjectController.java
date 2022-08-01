package com.java.project3.controller;

import com.java.project3.dto.MajorDTO;
import com.java.project3.dto.SubjectDTO;
import com.java.project3.dto.base.Page;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.dto.base.SearchResDto;
import com.java.project3.service.CourseServcice;
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
public class SubjectController {
    @Autowired
    SubjectService subjectService;
    @Autowired
    MajorService majorService;
    @Autowired
    CourseServcice courseServcice;

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

//    @GetMapping("quan-ly-mon-hoc")
//    public String majorDetail(
//            Model model,
//            @RequestParam(value = "currentPage", required = false) Integer currentPage,
//            @RequestParam(value = "pageSize", required = false) Integer pageSize,
//            @RequestParam(value = "type", required = false) String type,
//            @RequestParam(value = "search", required = false) String search,
//            @RequestParam(value = "courseId", required = false) Long courseId
//    ) {
//        Page page = new Page();
//        page = PageUltil.setDefault(currentPage, pageSize);
//        ResponseDto responseDto = subjectService.searchSubjectBy(page.getCurrentPage() - 1, page.getPageSize(), search, "Chuyên ngành");
//        SearchResDto searchResDto = (SearchResDto) responseDto.getObject();
//        model.addAttribute("findAll", searchResDto.getData());
//        page = PageUltil.format(currentPage, searchResDto.getTotalPages(), pageSize);
//        model.addAttribute("page", page);
//        model.addAttribute("search", search);
//
//        //get khóa
//        SearchReqDto searchReqDtoKhoa = new SearchReqDto();
//        searchReqDtoKhoa.setPageSize(100);
//        searchReqDtoKhoa.setPageIndex(0);
//        ResponseDto responseDtoKhoa = courseServcice.search(searchReqDtoKhoa);
//        SearchResDto searchResDtoKhoa = (SearchResDto) responseDtoKhoa.getObject();
//        model.addAttribute("khoa", searchResDtoKhoa.getData());
//        model.addAttribute("courseId", courseId);
//
//        //get ngành
//        SearchReqDto searchReqDtoNganh = new SearchReqDto();
//        searchReqDtoNganh.setPageSize(100);
//        searchReqDtoNganh.setPageIndex(0);
//        ResponseDto responseDtoNganh = majorService.search(searchReqDtoNganh);
//        SearchResDto searchResDtoNganh = (SearchResDto) responseDtoNganh.getObject();
//        model.addAttribute("nganh", searchResDtoNganh.getData());
//
//        return "quan-ly-mon-hoc-chuyen-nganh";
//    }

    @PostMapping("createSubject")
    public String createSubject(
            @ModelAttribute SubjectDTO subjectDTO
    ) {
        ResponseDto responseDto = subjectService.create(subjectDTO);
        return "redirect:/quan-ly-mon-hoc";
    }

//    @GetMapping("getMajor2/{id}")
//    @ResponseBody
//    public List<MajorDTO> getMajor(
//            @PathVariable("id") Long id
//    ) {
//        ResponseDto responseDto = majorService.findByCourseId(id);
//        return (List<MajorDTO>) responseDto.getObject();
//    }

//    @PostMapping("createSubjectDetail")
//    public String createSubjectDetail(
//            Model model,
//            @ModelAttribute SubjectDTO subjectDTO
//    ) {
//
//        List<MajorDTO> majorDTOS = null;
//        if (subjectDTO.getMajorId() != null) {
//            ResponseDto responseDto1 = majorService.findById(subjectDTO.getMajorId());
//            MajorDTO majorDTO = (MajorDTO) responseDto1.getObject();
//            model.addAttribute("courseId", majorDTO.getCourseId());
//
//            //get khóa
//            SearchReqDto searchReqDtoKhoa = new SearchReqDto();
//            searchReqDtoKhoa.setPageSize(100);
//            searchReqDtoKhoa.setPageIndex(0);
//            ResponseDto responseDtoKhoa = courseServcice.search(searchReqDtoKhoa);
//            SearchResDto searchResDtoKhoa = (SearchResDto) responseDtoKhoa.getObject();
//            model.addAttribute("khoa", searchResDtoKhoa.getData());
//            model.addAttribute("courseIds", majorDTO.getCourseId());
//
//            ResponseDto responseDto2 = majorService.findByCourseId(majorDTO.getCourseId());
//            majorDTOS = (List<MajorDTO>) responseDto2.getObject();
//            model.addAttribute("majorIdss", subjectDTO.getMajorId());
//
//        }
//        model.addAttribute("dataNganhs", majorDTOS);
//
//        ResponseDto responseDto = subjectService.createDetail(subjectDTO);
//        return "redirect:/quan-ly-mon-hoc-chuyen-nganh";
//    }




    @GetMapping("quan-ly-mon-hoc/{id}")
    public  String suaMon (
            @PathVariable("id") Long id,
            Model model
    ) {
        ResponseDto responseDto =  subjectService.findById(id);
        model.addAttribute("data", responseDto.getObject());
        return "fragment/body/home/edit/edit-subject";
    }

    @GetMapping("quan-ly-mon-hoc-chuyen-nganh/{id}")
    public  String suaMonChuyenNganh (
            @PathVariable("id") Long id,
            Model model
    ) {

        //get ngành
        SearchReqDto searchReqDtoNganh = new SearchReqDto();
        searchReqDtoNganh.setPageSize(100);
        searchReqDtoNganh.setPageIndex(0);
        ResponseDto responseDtoNganh = majorService.search(searchReqDtoNganh);
        SearchResDto searchResDtoNganh = (SearchResDto) responseDtoNganh.getObject();
        model.addAttribute("nganh", searchResDtoNganh.getData());

        ResponseDto responseDto =  subjectService.findById(id);
        model.addAttribute("data", responseDto.getObject());
        return "fragment/body/home/edit/edit-subject-detail";
    }

    @PutMapping("updateSubject")
    public String updateSubject(
            @ModelAttribute SubjectDTO subjectDTO
    ) {
        ResponseDto responseDto = subjectService.update(subjectDTO);
        return "redirect:/quan-ly-mon-hoc";
    }

    @PutMapping("updateSubjectDetail")
    public String updateSubjectDetail(
            @ModelAttribute SubjectDTO subjectDTO
    ) {
        ResponseDto responseDto = subjectService.update(subjectDTO);
        return "redirect:/quan-ly-mon-hoc-chuyen-nganh";
    }

    @DeleteMapping("deleteSubject")
    public String deleteSubject(
            @RequestParam(name = "id") Long id
    ) {
        ResponseDto responseDto = subjectService.delete(id);
        return "redirect:/quan-ly-mon-hoc";
    }

    @DeleteMapping("deleteSubjectDetail")
    public String deleteSubjectDetail(
            @RequestParam(name = "id") Long id
    ) {
        ResponseDto responseDto = subjectService.delete(id);
        return "redirect:/quan-ly-mon-hoc-chuyen-nganh";
    }
}
