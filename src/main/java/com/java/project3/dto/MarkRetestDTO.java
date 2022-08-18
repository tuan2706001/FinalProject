package com.java.project3.dto;

import com.googlecode.jmapper.annotations.JMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MarkRetestDTO {
    @JMap
    private Long id;

    @JMap
    private Long studentId;

    private String studentName;

    @JMap
    private Long ctdtSubjectClassId;

    private String ctdtSubjectClassName;

    private String subjectName;

    @JMap
    private Integer retestTheory;

    @JMap
    private Integer retestSkill;

    @JMap
    private Integer status;
}
