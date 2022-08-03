package com.java.project3.repository;

import com.java.project3.domain.CtdtSubjectClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CtdtSubjectClassRepository extends JpaRepository<CtdtSubjectClass, Long>, JpaSpecificationExecutor<CtdtSubjectClass> {
}
