package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Table(name = "workoutPlan")
public class WorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWorkoutPlan;

    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) //orphanRemoval per rimuovere gli esercizi associati quando si elimina il piano
    @JoinColumn(name = "athlete_id")
    private ArrayList<ExerciseWorkoutPlan> exercises;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_trainer_tax_code")
    private PersonalTrainer personalTrainer;

    @OneToOne
    @JoinColumn(name = "athlete_id")
    private Athlete athlete;



    public WorkoutPlan(LocalDate date, PersonalTrainer personalTrainer, Athlete athlete) {
        this.date = date;
        this.exercises = new ArrayList<>();
        this.personalTrainer = personalTrainer;
        this.athlete = athlete;
    }

    protected WorkoutPlan() {}




    public void addExercise(ExerciseWorkoutPlan exercise) {
        this.exercises.add(exercise);
    }

    public Long getIdWorkoutPlan() {
        return idWorkoutPlan;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<ExerciseWorkoutPlan> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<ExerciseWorkoutPlan> exercises) {
        this.exercises = exercises;
    }

    public void setPersonalTrainer(PersonalTrainer personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    public PersonalTrainer getPersonalTrainer() {
        return personalTrainer;
    }

    public Athlete getAthlete() {
        return athlete;
    }

}