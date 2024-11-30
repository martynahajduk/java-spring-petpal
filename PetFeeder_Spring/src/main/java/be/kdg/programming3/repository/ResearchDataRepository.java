package be.kdg.programming3.repository;

import be.kdg.programming3.domain.ResearchDataLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResearchDataRepository extends JpaRepository<ResearchDataLog, Long> {
    @Query("SELECT pdl.id, p.age AS age_weeks, pdl.petWeight AS weightGrams, p.animalType AS breed FROM PetDataLog pdl JOIN User u ON pdl.feeder = u.feeder JOIN Pet p ON u.pet = p")
    List<ResearchDataLog> getResearchDataLog();
}
