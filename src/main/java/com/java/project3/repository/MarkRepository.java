//package com.java.project3.repository;
//
//import com.java.project3.domain.CourseClass;
//import com.java.project3.domain.CtdtSubject;
//import com.java.project3.domain.Mark;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface MarkRepository extends JpaRepository<Mark, Long>, JpaSpecificationExecutor<Mark> {
//
//    @Query(value = "SELECT  s.* FROM #{#entityName} s WHERE s.subject_id = :subject_id AND s.grade_id = :grade_id", nativeQuery = true)
//    Page<Mark> findBySubjectIdAndGradeId(@Param("subject_id") CtdtSubject ctdtSubjectId, @Param("grade_id") CourseClass courseClassId, Pageable pageable);
//
//    Mark findByGradeId(Long gradeId);
//
//    List<Mark> findBySubjectId(Long id);
//
//    @Query(value = "SELECT  s.* FROM #{#entityName} s WHERE s.subject_id = :subject_id ", nativeQuery = true)
//    Mark searchBySubjectId(@Param("subject_id") Long subjectId);
//
//    @Query(value = "SELECT  s.* FROM #{#entityName} s WHERE s.grade_id = :grade_id ", nativeQuery = true)
//    List<Mark> searchByGradeId(@Param("grade_id") Long gradeId);
//
//    List<Mark> findByGradeIdAndSubjectId(Long gradeId, Long subjectId);
//
//    List<Mark> findByStudentId(Long studentId);
//
//    @Query(value = "SELECT  u.* FROM #{#entityName} u WHERE u.grade_id = :grade_id AND (lower(REPLACE(u.student_name,' ','')) like lower(CONCAT('%', (REPLACE(:search,' ','')),'%')) )" , nativeQuery = true)
//    Page<Mark> thongKeDiemTheoLop(@Param("grade_id") Long gradeId,
//                                  @Param("search") String search,
//                                  Pageable pageable);
//
//}
