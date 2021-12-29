package com.java.project3.repository;

import com.java.project3.domain.Coursse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KhoaRepository extends JpaRepository<Coursse, Long> {
}
