package com.java.project3.repository;

import com.java.project3.domain.Course;
import com.java.project3.domain.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MajorRepository extends JpaRepository<Major, Long>, JpaSpecificationExecutor<Major> {

    Major findByCourseId (Long courseId);
}
