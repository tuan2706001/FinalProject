package com.java.project3.controller;

import com.java.project3.dto.MajorDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MajorController {
    @Autowired
    MajorService majorService;


}
