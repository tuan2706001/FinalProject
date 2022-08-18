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
@Entity(name = EntityName.CTDT_SUBJECT_CLASS__STUDENT_RETEST)
@Table(name = EntityName.MAJOR)
public class CtdtSubjectClassStudentRetest extends AbstractBaseEntity {
    @Column(name = "ctdt_subject_class_id")
    private Long ctdtSubjectClassId;

    @Column(name = "student_id")
    private Long studentId;
}
