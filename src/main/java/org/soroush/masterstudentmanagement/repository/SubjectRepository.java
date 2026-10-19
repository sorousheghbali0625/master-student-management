package org.soroush.masterstudentmanagement.repository;


import org.soroush.masterstudentmanagement.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findByMasterId(Long masterId);

    boolean existsByTitle(String title);
}

