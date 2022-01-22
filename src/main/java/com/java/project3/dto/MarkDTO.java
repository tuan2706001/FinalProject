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
public class MarkDTO {
    @JMap
    private Long id;

    @JMap
    private Long studentId;

    @JMap
    private String studentName;

    @JMap
    private Long subjectId;

    @JMap
    private String subjectName;

    @JMap
    private Long gradeId;

    @JMap
    private String gradeName;

    @JMap
    private Short theory1;

    @JMap
    private Short theory2;

    @JMap
    private Short skill1;

    @JMap
    private Short skill2;

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
