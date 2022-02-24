package com.java.project3.repository;


import com.java.project3.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

//    @Query(value = "SELECT * FROM #{#entityName} where email = :email", nativeQuery = true)
//    List<User> findByEmail(String email);

    Optional<User> findByEmail(String email);

}
