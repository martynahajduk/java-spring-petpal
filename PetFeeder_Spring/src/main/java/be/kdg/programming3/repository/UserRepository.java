package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE :pet MEMBER OF u.pets")
    Optional<User> findUsersByPet(Pet pet);

}
