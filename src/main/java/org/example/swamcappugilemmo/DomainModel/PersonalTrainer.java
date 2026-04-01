package org.example.swamcappugilemmo.DomainModel;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "personalTrainer")
public class PersonalTrainer extends User {

    private int salary;
    private boolean active = false;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "personalTrainer")
    //Il "personalTrainer" è il nome dell'attributo in WorkoutPlan che fa riferimento a PersonalTrainer
    private List<WorkoutPlan> workoutPlans = new ArrayList<>();

    @OneToMany(mappedBy = "personalTrainer")
    //Il "personalTrainer" è il nome dell'attributo in Course che fa riferimento a PersonalTrainer
    private List<Course> courses = new ArrayList<>();



    public PersonalTrainer(String name, String surname, String username, String password, String email, String phone_number, String tax_code,
                           LocalDate birth_date, int salary, LocalDate startDate, LocalDate endDate) {
        super(name, surname, username, password, email, phone_number, tax_code, birth_date);
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    protected PersonalTrainer() {}




    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<WorkoutPlan> getWorkoutPlans() {
        return workoutPlans;
    }

    public void setWorkoutPlans(ArrayList<WorkoutPlan> workoutPlans) {
        this.workoutPlans = workoutPlans;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course){
        this.courses.add(course);
        //course.setPersonalTrainer(this);
    }

}
