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
public class GradeDTO {

    @JMap
    private Long id;

    @JMap
    private String name;

    @JMap
    private Long majorId;

    @JMap
    private String majorName;

    @JMap
    private Long courseId;

    @JMap
    private String courseName;

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
