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
            "left join mark m on m.student_id = s.id " +
            "where csc.id = :ctdtSubjectClassId and m.student_id is null ", nativeQuery = true)
    List<Student> findByCtdtSubjectClassId(@Param("ctdtSubjectClassId") Long ctdtSubjectClassId);

    @Query(value = "select * from student s " +
            "left join ctdt_subject_class__student_retest cscsr on s.id = cscsr.student_id " +
            "left join ctdt_subject_class csc on csc.id = cscsr.ctdt_subject_class_id " +
            "left join mark_retest mr on mr.student_id = cscsr.student_id " +
            "where csc.id = :ctdtSubjectClassId and mr.student_id is null ", nativeQuery = true)
    List<Student> findByCtdtSubjectClassRetestId(@Param("ctdtSubjectClassId") Long ctdtSubjectClassId);

    @Query(value = "select s.* from student s " +
            "left join course_class cc on cc.id = s.course_class_id " +
            "left join ctdt c on c.id = cc.ctdt_id " +
            "left join mark m on m.student_id = s.id " +
            "where c.id = :ctdtId and (m.theory2 < 5 or m.skill2 < 5)", nativeQuery = true)
    List<Student> findByCtdtId(@Param("ctdtId") Long ctdtId);

    @Query(value = "select (student_id) from ctdt_subject_class__student_retest where ctdt_subject_class_id = :ctdtSubjectClassId", nativeQuery = true)
    List<Long> findStudentByCtdtSubjectClassId(@Param("ctdtSubjectClassId") Long ctdtSubjectClassId);

    @Query(value = "select (full_name) from student where id = :studentId", nativeQuery = true)
    String findNameByStudentId(@Param("studentId") Long studentId);
}
