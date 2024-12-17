package be.kdg.programming3.domain;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name="feeder")
public class Feeder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = true)
    private Long id;

    // One Feeder can have multiple PetDataLogs
    @OneToMany(mappedBy = "feeder", fetch = FetchType.EAGER)

    private Set<PetDataLog> petDataLogs;



    public Feeder() {}

    public Feeder(double reservoirLevel , double bowlWeight,double petWeight,Long id) {

        this.id= id;

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<PetDataLog> getPetDataLogs() {
        return petDataLogs;
    }

    public void setPetDataLogs(Set<PetDataLog> petDataLogs) {
        this.petDataLogs = petDataLogs;
    }



    @Override
    public String toString() {
        return "" + id;
    }
}
