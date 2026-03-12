package org.example.swamcappugilemmo.DomainModel;
import jakarta.persistence.*;

@Entity
@Table(name = "exerciseWorkoutPlan")
public class ExerciseWorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEW;

    private int numSeries;
    private int numRepetitions;
    private double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    //nome della colonna nella tabella ExerciseWorkoutPlan che fa riferimento a Exercise
    private Exercise exercise;



    public ExerciseWorkoutPlan(int numSeries, int numRepetitions, double weight, Exercise exercise) {
        this.numSeries = numSeries;
        this.numRepetitions = numRepetitions;
        this.weight = weight;
        this.exercise = exercise;
    }

    protected ExerciseWorkoutPlan() {}




    public Long getIdEW() {
        return idEW;
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
