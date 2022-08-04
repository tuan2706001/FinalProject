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
public class SubjectDTO {

    @JMap
    private Long id;

    @JMap
    private String name;

    @JMap
    private String gioiThieu;

    private Long ctdtSubjectClassId;

}
