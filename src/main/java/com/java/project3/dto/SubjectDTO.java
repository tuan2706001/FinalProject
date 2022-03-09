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
public class SubjectDTO {

    @JMap
    private Long id;

    @JMap
    private String type;

    @JMap
    private String name;

    @JMap
    private Boolean theory;

    @JMap
    private Boolean skill;

    @JMap
    private Short duration;

    @JMap
    private Long majorId;

    @JMap
    private String majorName;

}
