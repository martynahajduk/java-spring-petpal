package be.kdg.programming3.repository;

import be.kdg.programming3.domain.PetDataLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetDataLogRepository extends JpaRepository<PetDataLog, Long> {
}

