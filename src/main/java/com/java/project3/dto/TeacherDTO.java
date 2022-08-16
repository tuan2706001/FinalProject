package com.java.project3.dto;

import com.googlecode.jmapper.annotations.JMap;
import com.java.project3.domain.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {

    @JMap
    private Long id;

    @JMap
    private String name;

    private List<Long> subjectIds;

    private List<String> subjectNames;

    private Long sumSubject;
}
