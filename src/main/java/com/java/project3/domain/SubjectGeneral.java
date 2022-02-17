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
@Entity(name = EntityName.SUBJECT_GENERAL)
@Table(name = EntityName.SUBJECT_GENERAL)
public class SubjectGeneral extends AbstractBaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "theory")
    private Boolean theory;

    @Column(name = "skill")
    private Boolean skill;

    @Column(name = "duration")
    private Short duration;

}
