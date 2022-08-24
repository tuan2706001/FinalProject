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

    private String studentName;

    private String studentCode;

    @JMap
    private Long ctdtSubjectClassId;

    private String ctdtSubjectClassName;

    private String subjectName;

    @JMap
    private Integer theory1;

    @JMap
    private Integer theory2;

    @JMap
    private Integer skill1;

    @JMap
    private Integer skill2;

    private Integer retestTheory;

    private Integer retestSkill;

    @JMap
    private Integer status;

//    private ThongSoThongKeDTO thongSoThongKeDTO;

//    private Integer sumQuaMon;
//
//    private Integer sumThiLai;
//
//    private Integer sumHocLai;

}
