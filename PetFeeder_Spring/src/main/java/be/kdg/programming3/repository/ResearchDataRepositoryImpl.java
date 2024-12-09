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
    public List<Map<String,Object>> findAllResearchData() {
        return em.createNativeQuery("SELECT * FROM prediction_data.hamster_data")
                .getResultList();
    }
}
