package be.kdg.programming3.domain;

public class Pet {
    String name;
    int age;
    Genders gender;
    String animalType;
    double weight;

    public Pet(String name, int age, Genders gender, String animalType, double weight) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.animalType = animalType;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Genders getGender() {
        return gender;
    }

    public void setGender(Genders gender) {
        this.gender = gender;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String toString() {
        return name + " " + age + " " + gender + " " + animalType + " " + weight;
    }
}
