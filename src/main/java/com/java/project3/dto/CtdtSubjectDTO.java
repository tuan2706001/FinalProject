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
public class CtdtSubjectDTO {
    @JMap
    private Long id;

    @JMap
    private String maMon;

    @JMap
    private Integer type;

    @JMap
    private Integer time;

    @JMap
    private Long ctdtId;

    @JMap
    private Long subjectId;

    private String ctdtName;

    private String subjectName;


}
