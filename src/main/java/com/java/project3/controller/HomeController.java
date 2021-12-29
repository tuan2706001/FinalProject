package com.java.project3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    // lúc đầu vào thì nó luôn tìm file index trong template để vào đầu tiên, mặc dừ k có đường dẫn đến đó

    @GetMapping("trang-chu")
    public String homePage() {
        // mở dự án kia ra
        return "HomePage/homePage";
    }

    @GetMapping("quan-ly-khoa")
    public String khoa() {
        // mở dự án kia ra
        return "quan-ly-khoa";
    }

    @GetMapping("quan-ly-sinh-vien")
    public String student() {
        // mở dự án kia ra
        return "quan-ly-sinh-vien";
    }

    @GetMapping("thong-tin-ca-nhan")
    public String info() {
        // mở dự án kia ra
        return "thong-tin-ca-nhan";
    }
}
