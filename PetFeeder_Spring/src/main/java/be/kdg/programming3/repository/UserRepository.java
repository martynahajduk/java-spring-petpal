package be.kdg.programming3.repository;

import be.kdg.programming3.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
