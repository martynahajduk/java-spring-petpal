package be.kdg.programming3.repository;

import be.kdg.programming3.domain.PetDataLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetDataLogRepository extends JpaRepository<PetDataLog, Long> {
    @Query("SELECT pdl FROM PetDataLog pdl WHERE pdl.id = (SELECT MAX(p.id) FROM PetDataLog p)")
    PetDataLog findLatestLog();
}

