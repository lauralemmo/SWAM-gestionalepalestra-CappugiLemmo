package org.example.swamcappugilemmo.BusinessLogic.DTO;

public class CourseDTO {

    private String name;
    private int numMembers;
    private int numMax;
    private String taxCode;



    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public int getNumMembers() {return numMembers;}

    public void setNumMembers(int numMembers) {this.numMembers = numMembers;}

    public int getNumMax() {return numMax;}

    public void setNumMax(int numMax) {this.numMax = numMax;}

    public String getTaxCode() {return taxCode;}

    public void setTaxCode(String taxCode) {this.taxCode = taxCode;}
}
