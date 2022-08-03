package com.java.project3.repository;

import com.java.project3.domain.CourseClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseClassRepository extends JpaRepository<CourseClass, Long>, JpaSpecificationExecutor<CourseClass> {


    List<CourseClass> findByCtdtId(Long ctdtId);

//    @Query(value = "SELECT  s.* FROM #{#entityName} s WHERE s.major_id = :major_id", nativeQuery = true)
//    List<CourseClass> search(@Param("major_id") Long majorId);

//    Long countByMajorId(Long majorId);

}
