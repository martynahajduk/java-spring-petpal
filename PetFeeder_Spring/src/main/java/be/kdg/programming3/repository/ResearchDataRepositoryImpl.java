package be.kdg.programming3.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ResearchDataRepositoryImpl implements ResearchDataRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Map<String, Object>> findAllResearchData() {
        return em.createNativeQuery("SELECT age_weeks, weight_grams AS pet_weight, food_intake, breed, sex " +
                        "FROM prediction_data.hamster_data WHERE age_weeks IS NOT NULL", Map.class)
                .getResultList();
    }
}
