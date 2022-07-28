package com.java.project3.repository;

import com.java.project3.domain.Major;
import com.java.project3.domain.CtdtSubject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<CtdtSubject, Long>, JpaSpecificationExecutor<CtdtSubject> {

//    @Query(value = "SELECT  s.* FROM #{#entityName} s WHERE s.major_id = :major_id", nativeQuery = true)
//    Page<CtdtSubject> search(@Param("major_id") Major majorId, Pageable pageable);
//
//    List<CtdtSubject> findByMajorId(Long majorId);
//    List<CtdtSubject> findByMajorIdNull();

}
