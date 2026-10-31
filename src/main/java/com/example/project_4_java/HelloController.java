package com.example.project_4_java;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class HelloController {

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
    }

    @FXML
    protected void onHelloButtonClick() {
        students = CollegeDB.getStudents();
//        StringBuilder listOfStudents = new StringBuilder();
//        for ( Student student : students ){
//            listOfStudents.append(student.toString()).append("\n");
//        }
//        welcomeText.setText(listOfStudents.toString());
        studentListView.getItems().clear();
        studentListView.getItems().addAll(students);
    }

    @FXML
    protected void selectedStudentButtonClicked() {
        Student selectedStudent = studentListView.getSelectionModel().getSelectedItem();
        welcomeText.setText(selectedStudent.toString());

        deleteStudentButton.setDisable(false);

    }

    @FXML
    protected void addStudentButtonClicked() {
        Student student = new Student(0, firstNameTextField.getText(), lastNameTextField.getText(), majorTextField.getText(), Double.parseDouble(gpaTextField.getText()));
        CollegeDB.addStudent(student);

        // reload list of students
        onHelloButtonClick();

    }

    @FXML
    protected void deleteStudentButtonClicked() {

        Student selectedStudent = studentListView.getSelectionModel().getSelectedItem();
        CollegeDB.deleteStudent(selectedStudent);

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
}