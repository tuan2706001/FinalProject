package com.java.project3.repository;

import com.java.project3.domain.Major;
import com.java.project3.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    List<Student> findByGradeId(Long gradeId);

    @Query(value = "SELECT c.* FROM #{#entityName} c WHERE c.grade_id = :grade_id", nativeQuery = true)
    Student delete(@Param("grade_id") Long gradeId);

    Long countStudentByGradeId(Long gradeId);

    Long countStudentByEmail(String email);
}
