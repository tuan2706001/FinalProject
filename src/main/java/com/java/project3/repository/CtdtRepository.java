package com.java.project3.repository;

import com.java.project3.domain.Ctdt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CtdtRepository extends JpaRepository<Ctdt, Long>, JpaSpecificationExecutor<Ctdt> {
        List<Ctdt> findByMajorId(Long majorId);

        @Query(value = "select (name) from ctdt where id = :ctdtId", nativeQuery = true)
        String findNameByCtdtId(@Param("ctdtId") Long ctdtId);
}
