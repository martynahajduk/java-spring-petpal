package be.kdg.programming3.repository;

import be.kdg.programming3.domain.SimulatedHeartbeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulatedHeartbeatRepository extends JpaRepository<SimulatedHeartbeatEntity,Long> {
}
