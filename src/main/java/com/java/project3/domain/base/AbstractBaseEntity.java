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

}
