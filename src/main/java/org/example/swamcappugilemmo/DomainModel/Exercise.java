package org.example.swamcappugilemmo.DomainModel;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "exercise")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExercise;

    @Column(unique = true)
    private String name;
    private String description;

    @OneToMany(mappedBy = "exercise")
    private List<ExerciseWorkoutPlan> specificExercises;


}