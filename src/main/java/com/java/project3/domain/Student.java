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
import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = EntityName.STUDENT)
@Table(name = EntityName.STUDENT)
public class Student extends AbstractBaseEntity {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "student_code")
    private String studentCode;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private Short gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "address")
    private String address;

    @Column(name = "course_class_id")
    private Long courseClassId;

    @Column(name = "course_class_name")
    private String courseClassName;
}
