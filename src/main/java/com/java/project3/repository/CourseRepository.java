package com.java.project3.repository;

import com.java.project3.domain.Coursse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseRepository extends JpaRepository<Coursse, Long>, JpaSpecificationExecutor<Coursse> {
}
