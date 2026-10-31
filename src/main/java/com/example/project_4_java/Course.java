package com.example.project_4_java;

public class Course {

    private int ID;
    private String department;
    private int number;
    private String title;
    private int credits;

    public Course(int ID, String department, int number, String title, int credits){
        setID(ID);
        setDepartment(department);
        setNumber(number);
        setTitle(title);
        setCredits(credits);
    }

    public int getID(){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public String getDepartment(){
        return department;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits){
        this.credits = credits;
    }

    @Override
    public String toString(){
        return "Course{" +
                "ID=" + ID +
                ", department='" + department + '\'' +
                ", number='" + number + '\'' +
                ", title='" + title + '\'' +
                ", credits=" + credits +
                '}';
    }
}
