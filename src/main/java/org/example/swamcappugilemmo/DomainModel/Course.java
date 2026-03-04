package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCourse;
    private String name;
    private int numMembers;
    private int numMax;
    boolean isBookable;
    @OneToMany
    private ArrayList<Booking> bookings;
    @ElementCollection
    @CollectionTable(name = "course_occurrences", joinColumns = @JoinColumn(name = "course_id"))
    private ArrayList<Occurrence> occurrences;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_trainer_code")
    private PersonalTrainer personalTrainer;

    public Course(String name, int numMembers, int numMax) {
        this.name = name;
        this.numMembers = numMembers;
        this.numMax = numMax;
        this.bookings = new ArrayList<>();
        this.occurrences = new ArrayList<>();
    }

    protected Course() {

    }


    public int getId() {
        return idCourse;
    }

    public void setId(int id) {
        this.idCourse = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumMembers() {
        return numMembers;
    }

    public void setNumMembers(int numMembers) {
        this.numMembers = numMembers;
    }

    public int getNumMax() {
        return numMax;
    }

    public void setNumMax(int numMax) {
        this.numMax = numMax;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    public ArrayList<Occurrence> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(ArrayList<Occurrence> occurrences) {
        this.occurrences = occurrences;
    }

    public PersonalTrainer getPersonalTrainer() {
        return personalTrainer;
    }

    public void setPersonalTrainer(PersonalTrainer personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    public boolean isBookable() {
        return this.personalTrainer != null && this.numMembers < this.numMax;
    }
}