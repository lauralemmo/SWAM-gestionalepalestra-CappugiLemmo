package org.example.swamcappugilemmo.DomainModel;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "personalTrainer")
public class PersonalTrainer extends User {

    private int salary;
    private boolean active;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "personalTrainer")
    //Il "personalTrainer" è il nome dell'attributo in WorkoutPlan che fa riferimento a PersonalTrainer
    private List<WorkoutPlan> workoutPlans;

    @OneToMany(mappedBy = "personalTrainer")
    //Il "personalTrainer" è il nome dell'attributo in Course che fa riferimento a PersonalTrainer
    private List<Course> courses;


    /*public void addCourse(Course course){
        this.courses.add(course);
        //course.setPersonalTrainer(this);
    }*/

}
