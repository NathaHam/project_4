package com.example.project_4_java;

public class Enrollment {

    private int ID;
    private int studentID;
    private int courseID;
    private String grade;

    public Enrollment(int ID, int studentID, int courseID, String grade) {
        this.ID = ID;
        this.studentID = studentID;
        this.courseID = courseID;
        this.grade = grade;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "ID=" + ID +
                ", studentID=" + studentID +
                ", courseID=" +courseID +
                ", grade='" + grade +'\'' +
                '}';
    }
    @Override
    public boolean equals(Object other){
        if (this == other){
            return true;
        }
        if (other == null || getClass() != other.getClass()){
            return false;
        }

        Enrollment enrollment = (Enrollment) other;

        return ID == enrollment.ID
                && studentID == enrollment.studentID
                && courseID == enrollment.courseID
                && java.util.Objects.equals(grade, enrollment.grade);
    }

}
