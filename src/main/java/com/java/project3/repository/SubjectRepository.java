package com.java.project3.repository;

import com.java.project3.domain.Major;
import com.java.project3.domain.CtdtSubject;
import com.java.project3.domain.Subject;
import com.java.project3.domain.TeacherSubject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long>, JpaSpecificationExecutor<Subject>  {

//    @Query(value = "SELECT  s.* FROM #{#entityName} s WHERE s.major_id = :major_id", nativeQuery = true)
//    Page<CtdtSubject> search(@Param("major_id") Major majorId, Pageable pageable);
//
//    List<CtdtSubject> findByMajorId(Long majorId);
//    List<CtdtSubject> findByMajorIdNull();

    @Query(value = "select (name) from subject where id = :subjectId", nativeQuery = true)
    List<String> findNameBySubjectId(@Param("subjectId") Long subjectId);

    @Query(value = "select (name) from subject where id = :subjectId", nativeQuery = true)
    String findNameById(@Param("subjectId") Long subjectId);

    @Query(value = "select * from subject s " +
            "left join ctdt_subject cs on cs.subject_id = s.id " +
            "left join ctdt_subject_class csc on csc.ctdt_subject_id = cs.id " +
            "where csc.id = :ctdtSubjectClassId ", nativeQuery = true)
    List<Subject> findByCtdtSubjectClassId(@Param("ctdtSubjectClassId") Long ctdtSubjectClassId);

    @Query(value = "select * from subject s " +
            "left join ctdt_subject cs on cs.subject_id = s.id " +
            "left join ctdt_subject_class csc on csc.ctdt_subject_id = cs.id " +
            "where csc.id = :ctdtSubjectClassId ", nativeQuery = true)
    Subject findByCtdtSubjectClassIdOne(@Param("ctdtSubjectClassId") Long ctdtSubjectClassId);

}
