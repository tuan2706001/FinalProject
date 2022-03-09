package com.java.project3.dto;

import com.googlecode.jmapper.annotations.JMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MinistryDTO {

    @JMap
    private Long id;

    @JMap
    private String fullName;

    @JMap
    private String username;

    @JMap
    private String email;

    @JMap
    private String phone;

    @JMap
    private String password;

}
