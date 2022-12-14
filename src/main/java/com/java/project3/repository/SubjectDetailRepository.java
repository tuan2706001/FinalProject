package com.java.project3.repository;

import com.java.project3.domain.CtdtSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubjectDetailRepository extends JpaRepository<CtdtSubject, Long>, JpaSpecificationExecutor<CtdtSubject> {
}
