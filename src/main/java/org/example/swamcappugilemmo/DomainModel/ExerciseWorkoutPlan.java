package org.example.swamcappugilemmo.DomainModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name = "workoutPlan_id")
    private WorkoutPlan workoutPlan;

}
