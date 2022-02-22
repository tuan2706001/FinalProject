package com.java.project3.repository;

import com.java.project3.domain.Grade;
import com.java.project3.domain.Major;
import com.java.project3.domain.Mark;
import com.java.project3.domain.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MarkRepository extends JpaRepository<Mark, Long>, JpaSpecificationExecutor<Mark> {

    @Query(value = "SELECT  s.* FROM #{#entityName} s WHERE s.subject_id = :subject_id AND s.grade_id = :grade_id", nativeQuery = true)
    Page<Mark> findBySubjectIdAndGradeId(@Param("subject_id") Subject subjectId, @Param("grade_id") Grade gradeId, Pageable pageable);

    Mark findByGradeId(Long gradeId);

}
