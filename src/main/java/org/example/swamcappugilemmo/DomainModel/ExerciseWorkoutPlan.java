package org.example.swamcappugilemmo.DomainModel;
import jakarta.persistence.*;

@Entity
public class ExerciseWorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEW;
    private int numSeries;
    private int numRepetitions;
    private double weight;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    //nome della colonna nella tabella ExerciseWorkoutPlan che fa riferimento a Exercise
    private Exercise exercise;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_plan_id")
    //nome della colonna nella tabella ExerciseWorkoutPlan che fa riferimento a WorkoutPlan
    private WorkoutPlan workoutPlan;

    public ExerciseWorkoutPlan(int idEW, int numSeries, int numRepetitions, double weight, Exercise exercise, WorkoutPlan workoutPlan) {
        this.idEW = idEW;
        this.numSeries = numSeries;
        this.numRepetitions = numRepetitions;
        this.weight = weight;
        this.exercise = exercise;
        this.workoutPlan = workoutPlan;
    }

    //Costruttore senza idEW per permettere a JPA di generarlo automaticamente
    public ExerciseWorkoutPlan(int numSeries, int numRepetitions, double weight, Exercise exercise, WorkoutPlan workoutPlan) {
        this.idEW = idEW;
        this.numSeries = numSeries;
        this.numRepetitions = numRepetitions;
        this.weight = weight;
        this.exercise = exercise;
        this.workoutPlan = workoutPlan;
    }

    protected ExerciseWorkoutPlan() {

    }


    public int getIdEW() {
        return idEW;
    }

    public void setIdEW(int idEW) {
        this.idEW = idEW;
    }

    public int getNumSeries() {
        return numSeries;
    }

    public void setNumSeries(int numSeries) {
        this.numSeries = numSeries;
    }

    public int getNumRepetitions() {
        return numRepetitions;
    }

    public void setNumRepetitions(int numRepetitions) {
        this.numRepetitions = numRepetitions;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Exercise getExercise() {
        return exercise;
    }
}
