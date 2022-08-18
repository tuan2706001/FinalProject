package com.java.project3.repository;

import com.java.project3.domain.MarkRetest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MarkRetestRepository extends JpaRepository<MarkRetest, Long>, JpaSpecificationExecutor<MarkRetest> {
}
