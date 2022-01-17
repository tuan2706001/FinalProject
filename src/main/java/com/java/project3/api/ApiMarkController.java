package com.java.project3.api;

import com.java.project3.dto.MarkDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mark")
public class ApiMarkController {
    @Autowired
    MarkService markService;

    @PostMapping("/create")
    public ResponseDto create(@RequestBody MarkDTO markDTO) {
        return markService.create(markDTO);
    }
}
