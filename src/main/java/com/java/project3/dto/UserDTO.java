package com.java.project3.dto;

import com.googlecode.jmapper.annotations.JMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @JMap
    private Long id;

    @JMap
    private String fullName;

    @JMap
    private String email;

    @JMap
    private String phone;

    @JMap
    private String password;

    @JMap
    private Short gender;

    @JMap
    private Date birthday;

    @JMap
    private String address;

    @JMap
    private LocalDateTime createdAt;

    @JMap
    private Long createdBy;

    @JMap
    private String createdByName;

    @JMap
    private LocalDateTime updatedAt;

    @JMap
    private Long updatedBy;

    @JMap
    private String updatedByName;
}
