package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "workoutPlan")
public class WorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWorkoutPlan;

    private LocalDate date;

    @OneToMany(mappedBy = "workoutPlan", cascade = CascadeType.ALL, orphanRemoval = true) //orphanRemoval per rimuovere gli esercizi associati quando si elimina il piano
    private List<ExerciseWorkoutPlan> specificExercises;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_trainer_tax_code")
    private PersonalTrainer personalTrainer;

    @OneToOne
    @JoinColumn(name = "athlete_id")
    private Athlete athlete;



    /*public void addExercise(ExerciseWorkoutPlan exercise) {
        this.specificExercises.add(exercise);
    }*/


}