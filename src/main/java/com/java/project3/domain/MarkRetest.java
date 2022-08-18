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
@Entity(name = EntityName.MARK_RETEST)
@Table(name = EntityName.MARK_RETEST)
public class MarkRetest extends AbstractBaseEntity {
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "ctdt_subject_class_id")
    private Long ctdtSubjectClassId;

    @Column(name = "retest_theory")
    private Integer retestTheory;

    @Column(name = "retest_skill")
    private Integer retestSkill;

    @Column(name = "status")
    private Integer status;
}
