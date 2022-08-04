package com.java.project3.repository;

import com.java.project3.domain.Student;
import com.java.project3.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    List<Student> findByCourseClassId(Long CourseClassId);

    @Query(value = "SELECT c.* FROM #{#entityName} c WHERE c.course_class_id = :courseClassId", nativeQuery = true)
    Student delete(@Param("courseClassId") Long CourseClassId);

    Long countStudentByCourseClassId(Long CourseClassId);

    Long countStudentByEmail(String email);

    @Query(value = "select * from student s " +
            "left join course_class cc on cc.id = s.course_class_id " +
            "left join ctdt_subject_class csc on csc.course_class_id = cc.id " +
            "where csc.id = :ctdtSubjectClassId ", nativeQuery = true)
    List<Student> findByCtdtSubjectClassId(@Param("ctdtSubjectClassId") Long ctdtSubjectClassId);
}
