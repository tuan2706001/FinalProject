package com.java.project3.repository;

import com.java.project3.domain.SubjectGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubjectRepository extends JpaRepository<SubjectGeneral, Long>, JpaSpecificationExecutor<SubjectGeneral> {
}
