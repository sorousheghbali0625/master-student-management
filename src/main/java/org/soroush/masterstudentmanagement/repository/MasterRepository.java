package org.soroush.masterstudentmanagement.repository;



import org.soroush.masterstudentmanagement.model.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {

    Optional<Master> findByEmail(String email);

    boolean existsByEmail(String email);
}
