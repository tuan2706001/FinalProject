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
public class CtdtSubjectClassStudentRetestDTO {
    @JMap
    private Long id;

    @JMap
    private Long ctdtSubjectClassId;

    @JMap
    private Long studentId;
}
