package be.kdg.programming3.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


public interface ResearchDataRepository {
    List<Map<String,Object>> findAllResearchData();

//    @Query(value="SELECT * FROM prediction_data.hamster_data",nativeQuery = true)
//    List<Map<String,Object>> findAllResearchData();
}
