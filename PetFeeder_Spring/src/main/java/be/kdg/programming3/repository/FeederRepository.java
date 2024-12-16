package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Feeder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeederRepository extends JpaRepository<Feeder, Long> {
    Feeder findByPetId(Long petId);
}
