package com.java.project3.domain.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.actuate.audit.listener.AuditListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditListener.class)
public abstract class AbstractBaseEntity implements AuditAuto, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "created_at")
    @JsonIgnore
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    @JsonIgnore
    private Long createdBy;

    @Column(name = "created_by_name")
    @JsonIgnore
    private String createdByName;

    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    @JsonIgnore
    private Long updatedBy;

    @Column(name = "updated_by_name")
    @JsonIgnore
    private String updatedByName;

    @Column(name = "is_deleted")
    @JsonIgnore
    private Boolean isDeleted;
}
