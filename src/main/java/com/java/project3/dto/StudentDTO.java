package com.java.project3.dto;

import com.googlecode.jmapper.annotations.JMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    @JMap
    private Long id;

    @JMap
    private String fullName;

    @JMap
    private String studentCode;

    @JMap
    private String email;

    @JMap
    private String phone;

    @JMap
    private String password;

    @JMap
    private Short gender;

    @JMap
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @JMap
    private String address;

    @JMap
    private Long courseClassId;

}
