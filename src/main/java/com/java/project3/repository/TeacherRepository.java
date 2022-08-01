package com.java.project3.repository;

import com.java.project3.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface TeacherRepository extends JpaRepository<Teacher, Long>, JpaSpecificationExecutor<Teacher> {

    @Modifying
    @Transactional
    @Query(
            value = "insert into teacher_subject (id, teacher_id , subject_id) values (:id, :teacherId, :subjectId) ",
            nativeQuery = true
    )
    void addSubjectToTeacher(@Param("id") Long id, @Param("teacherId") Long teacher, @Param("subjectId") Long subjectId);

    @Query(value = "select count(*) from teacher_subject where teacher_id = :teacherId", nativeQuery = true)
    Long countSubjectByTeacher(@Param("teacherId") Long teacherId);

}
