package com.java.project3.domain.base;

import java.time.LocalDateTime;

public interface AuditAuto {

    Long getId();

    void setId(Long id);

    LocalDateTime getCreatedAt();

    void setCreatedAt(LocalDateTime now);

    Long getCreatedBy();

    void setCreatedBy(Long userId);

    String getCreatedByName();

    void setCreatedByName(String fullName);

    LocalDateTime getUpdatedAt();

    void setUpdatedAt(LocalDateTime now);

    Long getUpdatedBy();

    void setUpdatedBy(Long userId);

    String getUpdatedByName();

    void setUpdatedByName(String fullName);

    Boolean getIsDeleted();

    void setIsDeleted(Boolean isDeleted);
}
