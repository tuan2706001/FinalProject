package com.java.project3.domain;

import com.java.project3.constant.EntityName;
import com.java.project3.domain.base.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = EntityName.SUBJECT)
@Table(name = EntityName.SUBJECT)
public class Subject extends AbstractBaseEntity {

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "theory")
    private Boolean theory;

    @Column(name = "skill")
    private Boolean skill;

    @Column(name = "duration")
    private Short duration;

    @Column(name = "major_id")
    private Long majorId;

    @Column(name = "major_name")
    private String majorName;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_name")
    private String courseName;
}
