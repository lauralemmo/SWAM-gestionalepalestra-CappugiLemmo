package org.example.swamcappugilemmo.DomainModel;


import jakarta.persistence.*;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idExercise;
    @Column(unique = true)
    private String name;
    private String description;

    public Exercise(int idExercise, String name, String description) {
        this.idExercise = idExercise;
        this.name = name;
        this.description = description;
    }

    public Exercise(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected Exercise() {

    }
//metodi getter e setter
    
    public int getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(int idExercise) {
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