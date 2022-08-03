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
@Entity(name = EntityName.CTDT_SUBJECT)
@Table(name = EntityName.CTDT_SUBJECT)
public class CtdtSubject extends AbstractBaseEntity {

    @Column(name = "ma_mon")
    private String maMon;

    @Column(name = "type")
    private Integer type;

    @Column(name = "time")
    private Integer time;

    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "ctdt_id")
    private Long ctdtId;
}
