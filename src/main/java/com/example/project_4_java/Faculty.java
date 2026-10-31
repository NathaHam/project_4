package com.example.project_4_java;

public class Faculty {

    private int ID;
    private String firstName;
    private String lastName;
    private String department;

    public Faculty(int ID, String firstName, String lastName, String department){
        setID(ID);
        setFirstName(firstName);
        setLastName(lastName);
        setDepartment(department);
    }

    public int getID(){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getDepartment(){
        return department;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
