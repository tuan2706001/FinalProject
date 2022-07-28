package com.java.project3.repository;

import com.java.project3.domain.Ctdt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CtdtRepository extends JpaRepository<Ctdt, Long>, JpaSpecificationExecutor<Ctdt> {
}
