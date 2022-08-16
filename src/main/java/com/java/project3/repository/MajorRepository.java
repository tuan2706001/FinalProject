package com.java.project3.repository;

import com.java.project3.domain.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long>, JpaSpecificationExecutor<Major> {

//    List<Major> findByCourseId(Long courseId);
//
//    @Query(value = "SELECT c.* FROM #{#entityName} c WHERE c.course_id = :course_id", nativeQuery = true)
//    Major delete(@Param("course_id") Long courseId);
//
//    @Query(value = "SELECT c.* FROM #{#entityName} c WHERE c.course_id = :course_id", nativeQuery = true)
//    Major findCourseId(@Param("course_id") Long courseId);
//
//    Long countByCourseId(Long courseId);

    @Query(value = "select * from ctdt " +
            "where id = :ctdtId", nativeQuery = true)
    Major findMajorById(@Param("ctdtId") Long ctdtId);
}
