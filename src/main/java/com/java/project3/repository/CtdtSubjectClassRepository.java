package com.java.project3.repository;

import com.java.project3.domain.CtdtSubjectClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CtdtSubjectClassRepository extends JpaRepository<CtdtSubjectClass, Long>, JpaSpecificationExecutor<CtdtSubjectClass> {

    @Query(value = "select * from ctdt_subject_class where course_class_id is null", nativeQuery = true)
    Page<CtdtSubjectClass> dsLopHocLai(Pageable pageable);

    @Query(value = "select * from ctdt_subject_class where course_class_id = :courseClassId and lop_thi_xong = true", nativeQuery = true)
    Page<CtdtSubjectClass> dsThongKeLop(Long courseClassId, Pageable pageable);

}
