package com.java.project3.dto.base;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
public abstract class AbstractBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @JMap
    private Long id;
}
