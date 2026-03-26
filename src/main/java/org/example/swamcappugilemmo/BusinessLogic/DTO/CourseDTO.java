package org.example.swamcappugilemmo.BusinessLogic.DTO;

public class CourseDTO {
    private Long idCourse;
    private String name;
    private int numMembers;
    private int numMax;

    public Long getIdCourse() {return idCourse;}

    public void setIdCourse(Long idCourse) {this.idCourse = idCourse;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public int getNumMembers() {return numMembers;}

    public void setNumMembers(int numMembers) {this.numMembers = numMembers;}

    public int getNumMax() {return numMax;}

    public void setNumMax(int numMax) {this.numMax = numMax;}
}
