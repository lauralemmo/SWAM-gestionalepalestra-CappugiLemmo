package org.example.swamcappugilemmo.DomainModel;


import jakarta.persistence.*;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExercise;
    @Column(unique = true)
    private String name;
    private String description;


    public Exercise(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected Exercise() {}



    
    public Long getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(Long idExercise) {
        this.idExercise = idExercise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}