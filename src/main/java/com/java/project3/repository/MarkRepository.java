package com.java.project3.repository;

import com.java.project3.domain.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MarkRepository extends JpaRepository<Mark, Long>, JpaSpecificationExecutor<Mark> {
}
