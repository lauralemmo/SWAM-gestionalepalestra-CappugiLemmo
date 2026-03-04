package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
public class WorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idWorkoutPlan;
    private LocalDate date;
    //mappedby indica che la relazione è gestita dal lato "ExerciseWorkoutPlan" attraverso l'attributo "workoutPlan"
    @OneToMany(mappedBy = "workoutPlan", cascade = CascadeType.ALL, orphanRemoval = true) //orphanRemoval per rimuovere gli esercizi associati quando si elimina il piano
    private ArrayList<ExerciseWorkoutPlan> exercises;
    private String personalTrainerName; // nome del personal trainer che ha creato il piano (è una stringa per semplici
    // tà) Se teniamo così possiamo rimuovere l'associazione con PersonalTrainer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_trainer_tax_code")
    private PersonalTrainer personalTrainer;

    public PersonalTrainer getPersonalTrainer() {
        return personalTrainer;
    }

    public void setPersonalTrainer(PersonalTrainer personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    public WorkoutPlan(LocalDate date) {
        this.date = date;
        this.exercises = new ArrayList<>();
    }

    public WorkoutPlan(int idWorkoutPlan, LocalDate date, String personalTrainerName) {
        this.idWorkoutPlan = idWorkoutPlan;
        this.personalTrainerName = personalTrainerName;
        this.date = date;
        this.exercises = new ArrayList<>();
    }

    public WorkoutPlan(String personalTrainer) {
        this.personalTrainerName = personalTrainer;
        this.exercises = new ArrayList<>();
    }

    protected WorkoutPlan() {
        this.exercises = new ArrayList<>();
    }




    public void addExercise(ExerciseWorkoutPlan exercise) {
        this.exercises.add(exercise);
    }

    public int getIdWorkoutPlan() {
        return idWorkoutPlan;
    }

    public void setIdWorkoutPlan(int idWorkoutPlan) {
        this.idWorkoutPlan = idWorkoutPlan;
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

    public String getPersonalTrainerName() {
        return personalTrainerName;
    }

    public void setPersonalTrainerName(String personalTrainerName) {
        this.personalTrainerName = personalTrainerName;
    }
    public void setPersonalTrainerName(PersonalTrainer pt) {
        this.personalTrainerName = pt.getName();
    }
}