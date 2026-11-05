package com.example.project_4_java;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class StudentController {

    @FXML
    private ListView<Student> studentListView;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField studentFilterTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField majorTextField;
    @FXML
    private TextField gpaTextField;
    @FXML
    private Button selectStudentButton;
    @FXML
    private Button deleteStudentButton;
    @FXML
    private Button addStudentButton;

    ArrayList<Student> students;

    public void initialize(){
        deleteStudentButton.setDisable(true);
        onHelloButtonClick();
    }

    @FXML
    protected void onHelloButtonClick() {
        students = CollegeDB.getStudents();
        studentListView.getItems().clear();
        studentListView.getItems().addAll(students);
    }

    @FXML
    protected void selectedStudentButtonClicked() {
        Student selectedStudent = studentListView.getSelectionModel().getSelectedItem();

        if(selectedStudent != null){
            welcomeText.setText(selectedStudent.toString());
            deleteStudentButton.setDisable(false);
        }
    }

    @FXML
    protected void addStudentButtonClicked() {

        Double gpa;
        try{
            gpa = Double.parseDouble(gpaTextField.getText());
            Student student = new Student(0, firstNameTextField.getText(), lastNameTextField.getText(), majorTextField.getText(), gpa);
            CollegeDB.addStudent(student);
        } catch (NumberFormatException e){
            showError("GPA has to be a floating number");
        }

        // reload list of students
        onHelloButtonClick();

    }

    @FXML
    protected void deleteStudentButtonClicked() {

        Student selectedStudent = studentListView.getSelectionModel().getSelectedItem();

        if(selectedStudent != null){
            CollegeDB.deleteStudent(selectedStudent);
        }
        // reload list of students
        onHelloButtonClick();

        deleteStudentButton.setDisable(true);
    }

    @FXML
    protected void studentFilterTyped() {
        String filterText = studentFilterTextField.getText();

        ArrayList<Student> filteredStudents = new ArrayList<>();

        for ( Student student : students ){
            if ( student.getLastName().contains(filterText) || student.getFirstName().contains(filterText)){
                filteredStudents.add(student);
            }
        }

        studentListView.getItems().clear();
        studentListView.getItems().addAll(filteredStudents);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}