package com.java.project3.repository;

import com.java.project3.domain.Major;
import com.java.project3.domain.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SubjectRepository extends JpaRepository<Subject, Long>, JpaSpecificationExecutor<Subject> {

//    @Query(value = "SELECT  s.* FROM #{#entityName} s WHERE s.type = :type AND s.name = :name", nativeQuery = true)
//    Page<Subject> search1(@Param("type")String type , @Param("name") String name, Pageable pageable);
//
//    @Query(value = "SELECT  s.* FROM #{#entityName} s WHERE s.type = :type AND s.name = :name", nativeQuery = true)
//    Page<Subject> search2(@Param("type")String type , @Param("name") String name, Pageable pageable);

    @Query(value = "SELECT  s.* FROM #{#entityName} s WHERE s.major_id = :major_id", nativeQuery = true)
    Page<Subject> findByMajorId(@Param("major_id") Major majorId, Pageable pageable);

}
