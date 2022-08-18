package com.java.project3.repository;

import com.java.project3.domain.CtdtSubjectClassStudentRetest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CtdtSubjectClassStudentRetestRepository extends JpaRepository<CtdtSubjectClassStudentRetest, Long>, JpaSpecificationExecutor<CtdtSubjectClassStudentRetest> {

    @Modifying
    @Transactional
    @Query(
            value = "insert into ctdt_subject_class__student_retest (id, student_id , ctdt_subject_class_id) values (:id, :studentId, :ctdtSubjectClassId) ",
            nativeQuery = true
    )
    void addStudentClassRetest(@Param("id") Long id, @Param("studentId") Long studentId, @Param("ctdtSubjectClassId") Long ctdtSubjectClassId);

//    Long countCtdtSubjectClassStudentRetestByCtdtSubjectClassId(Long ctdtSubjectClassId);

    @Query(value = " select count(*) from ctdt_subject_class__student_retest where ctdt_subject_class_id = :ctdtSubjectClassId ", nativeQuery = true)
    Long countCtdtSubjectClassStudentRetestByCtdtSubjectClassId(@Param("ctdtSubjectClassId") Long ctdtSubjectClassId);
}
