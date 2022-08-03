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
@Entity(name = EntityName.CTDT_SUBJECT_CLASS)
@Table(name = EntityName.CTDT_SUBJECT_CLASS)
public class CtdtSubjectClass extends AbstractBaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "ctdt_subject_id")
    private Long ctdtSubjectId;

    @Column(name = "course_class_id")
    private Long courseClassId;

    @Column(name = "ma_lop")
    private String maLop;

    @Column(name = "note")
    private String note;

    @Column(name = "teacher_id")
    private Long teacherId;
}
