package com.java.project3.repository;


import com.java.project3.domain.CtdtSubject;
import com.java.project3.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CtdtSubjectRepository extends JpaRepository<CtdtSubject, Long>, JpaSpecificationExecutor<CtdtSubject> {

    List<CtdtSubject> findByCtdtId(Long ctdtId);

    @Query(value = "select (name) from subject s " +
            "join ctdt_subject cs on cs.subject_id = s.id where cs.id = :ctdtSubjectId", nativeQuery = true)
    String findNameByCtdtSubjectId(@Param("ctdtSubjectId") Long ctdtSubjectId);

}
