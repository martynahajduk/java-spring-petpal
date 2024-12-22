package be.kdg.programming3.presentation.viewmodel;

import be.kdg.programming3.domain.Breed;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public class AddPetViewModel {
    @NotBlank(message = "Name is required.")
    @Size(min = 2, message = "Name must have at least 2 characters.")
    private String name;

    @NotNull(message = "Feeder ID is required.")
    private Long feederId;

    @NotNull(message = "Birth date is required.")
    @Past(message = "Birth date must be in the past.")
    private LocalDate birthDate;

    @NotNull(message = "Animal type is required.")
    private Breed animalType;

    @Positive(message = "Pet weight must be greater than 0.")
    private double petWeight;

    @NotBlank(message = "Sex is required.")
    @Pattern(regexp = "Male|Female", message = "Sex must be either 'Male' or 'Female'.")
    private String sex;

    @NotEmpty(message = "At least one user must be assigned.")
    private List<Long> userIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFeederId() {
        return feederId;
    }

    public void setFeederId(Long feederId) {
        this.feederId = feederId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Breed getAnimalType() {
        return animalType;
    }

    public void setAnimalType(Breed animalType) {
        this.animalType = animalType;
    }

    public double getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(double petWeight) {
        this.petWeight = petWeight;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}
