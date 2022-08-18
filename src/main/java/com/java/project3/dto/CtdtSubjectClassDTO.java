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
public class CtdtSubjectClassDTO {
    @JMap
    private Long id;

    @JMap
    private String name;

    @JMap
    private String maLop;

    @JMap
    private Long ctdtSubjectId;

    private String ctdtSubjectName;

    @JMap
    private Long courseClassId;

    private String courseClassName;

    @JMap
    private String note;

    @JMap
    private Long teacherId;

    private String teacherName;

    @JMap
    private Boolean lopThiXong;

}
