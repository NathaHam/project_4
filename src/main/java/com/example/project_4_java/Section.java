package com.example.project_4_java;

public class Section {

    private int ID;
    private int courseID;
    private int facultyID;
    private String semester;
    private String day;
    private String startTime;
    private String endTime;
    private String modality;

    public Section(int ID, int courseID, int facultyID, String semester, String day, String startTime, String endTime, String modality) {
        this.ID = ID;
        this.courseID = courseID;
        this.facultyID = facultyID;
        this.semester = semester;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.modality = modality;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(int facultyID) {
        this.facultyID = facultyID;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    @Override
    public String toString() {
        return "Section{" +
                "ID=" + ID +
                ", courseID=" + courseID +
                ", facultyID=" + facultyID +
                ", semester='" + semester + '\'' +
                ", day='" + day + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", modality='" + modality + '\'' +
                '}';
    }
}
