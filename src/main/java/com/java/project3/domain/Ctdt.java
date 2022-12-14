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
@Entity(name = EntityName.CTDT)
@Table(name = EntityName.CTDT)
public class Ctdt extends AbstractBaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "ma_chuong_trinh")
    private String maChuongTrinh;

    @Column(name = "major_id")
    private Long majorId;
}
