package be.kdg.programming3.collector;

public abstract class PetPalData {
    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetPalData(Long id) {
        this.id = id;
    }
    //method to allow subclasses to provide data handling logic

    public abstract void processData();
}
