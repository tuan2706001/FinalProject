package com.java.project3.repository;

import com.java.project3.domain.MarkRetest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MarkRetestRepository extends JpaRepository<MarkRetest, Long>, JpaSpecificationExecutor<MarkRetest> {

    @Query(value = "select distinct mr.* from student stu " +
            "    left join course_class cc on cc.id = stu.course_class_id " +
            "    left join mark_retest mr on stu.id = mr.student_id " +
            "    left join ctdt_subject_class csc on csc.id = mr.ctdt_subject_class_id " +
            "    left join ctdt_subject cs on cs.id = csc.ctdt_subject_id " +
            "    left join subject s on cs.subject_id = s.id " +
            "where mr.student_id = :studentId and s.id = :subjectId and csc.lop_thi_xong = true", nativeQuery = true)
    MarkRetest findByStudentIdAndSubjectId(@Param("studentId") Long studentId,
                                           @Param("subjectId") Long subjectId);
}
