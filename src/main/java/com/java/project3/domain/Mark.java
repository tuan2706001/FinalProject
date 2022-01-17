package com.java.project3.domain;

import com.java.project3.constant.EntityName;
import com.java.project3.domain.base.AbstractBaseEntity;
import com.java.project3.dto.base.SearchResDto;
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
@Entity(name = EntityName.MARK)
@Table(name = EntityName.MARK)
public class Mark extends AbstractBaseEntity {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "theory1")
    private Short theory1;

    @Column(name = "theory2")
    private Short theory2;

    @Column(name = "skill1")
    private Short skill1;

    @Column(name = "skill2")
    private Short skill2;


}
