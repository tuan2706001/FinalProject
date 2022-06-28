package com.java.project3.repository;

import com.java.project3.domain.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long>, JpaSpecificationExecutor<Curriculum> {
}
