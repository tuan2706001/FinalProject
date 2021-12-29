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
    @JMap
    private LocalDateTime createdAt;
    @JMap
    private Long createdBy;
    @JMap
    private String createdByName;
    @JMap
    private LocalDateTime updatedAt;
    @JMap
    private Long updatedBy;
    @JMap
    private String updatedByName;
    @JMap
    private Boolean isDeleted;
}
