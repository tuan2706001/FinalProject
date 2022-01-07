package com.java.project3.controller;

import com.java.project3.dto.MajorDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    MajorService majorService;

    @GetMapping(value = {"/trang-chu"})
    public String home() {
        return "trang-chu";
    }

    @GetMapping("quan-ly-nganh")
    public String major() {
//        @PathVariable("name") String name,
//        @RequestParam(value = "currentPage", required = false) Integer currentPage,
//        @RequestParam(value = "pageSize", required = false) Integer pageSize,
//        @RequestParam(value = "search", required = false) String search,
//        @RequestParam(value = "call_id", required = false) Long callId
//        Model model)
        return "quan-ly-nganh";
    }

    @GetMapping("quan-ly-khoa")
    public String khoa() {
        return "quan-ly-khoa";
    }

    @GetMapping("quan-ly-lop")
    public String grade() {
        return "quan-ly-lop";
    }

    @GetMapping("quan-ly-sinh-vien")
    public String student() {
        return "quan-ly-sinh-vien";
    }

    @GetMapping("quan-ly-mon-hoc")
    public String subject() {
        return "quan-ly-mon-hoc";
    }

    @GetMapping("quan-ly-diem")
    public String mark() {
        return "quan-ly-diem";
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
