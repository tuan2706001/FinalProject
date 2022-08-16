package com.java.project3.repository;

import com.java.project3.domain.TeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TearcherSubjectReposiotry extends JpaRepository<TeacherSubject, Long>, JpaSpecificationExecutor<TearcherSubjectReposiotry> {

    Long countTeacherSubjectByTeacherId(Long teacherId);

//    @Query(value = "select (subject_id) from teacher_subject where teacher_id = :teacherId", nativeQuery = true)
//    List<Long> findSubjectByTeacher(@Param("teacherId") Long teacherId);

    @Query(value = "select * from teacher_subject ts " +
            "left join ctdt_subject cs on cs.subject_id = ts.subject_id " +
            "where cs.id = :ctdtSubjectId", nativeQuery = true)
    List<TeacherSubject> findByCtdtSubjectId(@Param("ctdtSubjectId") Long ctdtSubjectId);
}
