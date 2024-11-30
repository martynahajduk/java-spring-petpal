package be.kdg.programming3.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

public class ResearchDataLog {

    @Id
    @Column(name = "id", nullable = false)
    long id;

    @Column(name = "age_weeks", nullable = false)
    int ageWeeks;

    @Column(name = "weight_grams", nullable = false)
    int weightGrams;

    @Enumerated(EnumType.STRING)
    @Column(name = "breed", nullable = false)
    Breed breed;

    @Column(name = "food_intake")
    int foodIntake;

    @Column(name="sex")
    String sex;
}
