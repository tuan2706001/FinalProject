package com.java.project3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    // lúc đầu vào thì nó luôn tìm file index trong template để vào đầu tiên, mặc dừ k có đường dẫn đến đó

    @GetMapping(value = {"/", "/trang-chu"})
    public String home() {
        return "trang-chu";
    }

    @GetMapping("quan-ly-nganh")
    public String major() {
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




}
