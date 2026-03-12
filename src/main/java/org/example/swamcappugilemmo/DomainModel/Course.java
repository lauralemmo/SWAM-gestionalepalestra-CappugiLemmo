package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCourse;

    private String name;
    private int numMembers;
    private int numMax;

    //@ElementCollection
    //@CollectionTable(name = "course_occurrences", joinColumns = @JoinColumn(name = "course_id"))
    @OneToMany(mappedBy = "course")
    private ArrayList<Occurrence> occurrences;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_trainer_tax_code")
    private PersonalTrainer personalTrainer;



    public Course(String name, int numMembers, int numMax, PersonalTrainer personalTrainer) {
        this.name = name;
        this.numMembers = numMembers;
        this.numMax = numMax;
        this.occurrences = new ArrayList<>();
        this.personalTrainer = personalTrainer;
    }

    protected Course() {}




    public Long getId() {
        return idCourse;
    }

    public void setId(Long id) {
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

}