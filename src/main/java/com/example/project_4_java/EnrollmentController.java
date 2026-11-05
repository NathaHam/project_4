package com.example.project_4_java;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnrollmentController {

    @FXML private ListView<Enrollment> enrollmentListView;
    @FXML private ComboBox<Student> studentComboBox;
    @FXML private ComboBox<Section> sectionComboBox;
    @FXML private ComboBox<String> gradeComboBox;
    @FXML private Button updateEnrollmentButton;
    @FXML private Button deleteEnrollmentButton;

    public void initialize() {
        gradeComboBox.getItems().addAll("A", "A-", "B+","B","B-","C+","C","C-","D+","D","D-","F");

        studentComboBox.setCellFactory(list -> new ListCell<>(){
            @Override
                    protected void updateItem(Student student, boolean empty){
                super.updateItem(student,empty);
                setText(empty || student == null ? null : student.getFirstName() + " " + student.getLastName()+  "(ID: "+ student.getID() + ")");
            }
        });

        studentComboBox.setButtonCell(new ListCell<>(){
            @Override
                    protected void updateItem(Student student, boolean empty){
                        setText(empty || student == null ? null: student.getFirstName() + " " + student.getLastName());
            }
        });

        sectionComboBox.setCellFactory(List -> new ListCell<>(){
            @Override
                    protected void updateItem(Section section, boolean empty){
                super.updateItem(section , empty);
                setText(empty || section == null ? null : describeSection(section));
            }
        });

        sectionComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Section section, boolean empty){
                super.updateItem(section,empty);
                setText(empty || section == null ? null : describeSection(section));
            }
        });

        enrollmentListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Enrollment enrollment, boolean empty){
                super.updateItem(enrollment,empty);
                setText(empty || enrollment == null ? null : describeEnrollment(enrollment));
            }
        });

        enrollmentListView.getSelectionModel().selectedItemProperty().addListener((obs,old,enrollment)-> fillForm(enrollment));

        loadEnrollments();
    }

    @FXML
    protected void loadEnrollments(){
        studentComboBox.getItems().setAll(CollegeDB.getStudents());

        try{
            sectionComboBox.getItems().setAll(CollegeDB.getSections());

            enrollmentListView.getItems().setAll(
                    CollegeDB.getEnrollment()
            );
        }catch (SQLException e){
            showError("Couldn't load enrollments: " + e.getMessage());
        }
    }

    @FXML
    protected void addEnrollmentButtonClicked(){
        save(false);
    }

    @FXML
    protected void updateEnrollmentButtonClicked(){
        save(true);
    }

    @FXML
    protected void deleteEnrollmentButtonClicked(){

        Enrollment selectedEnrollment = enrollmentListView.getSelectionModel().getSelectedItem();

        if (selectedEnrollment == null) {
            return;
        }

        try {
            boolean deleted = CollegeDB.deleteEnrollment(selectedEnrollment);
            if (deleted){
                loadEnrollments();
                clearForm();
            } else {
                showError("Couldn't delete enrollment.");
            }

        } catch (Exception e) {
            showError ("Couldn't delete enrollment: " + e.getMessage());
        }
    }

    @FXML
    protected void clearForm(){

        enrollmentListView.getSelectionModel().clearSelection();

        studentComboBox.setValue(null);
        sectionComboBox.setValue(null);
        gradeComboBox.setValue(null);

        updateEnrollmentButton.setDisable(true);
        deleteEnrollmentButton.setDisable(true);
    }

    private void save(boolean update) {
        Enrollment selectedEnrollment = enrollmentListView.getSelectionModel().getSelectedItem();

        if (update && selectedEnrollment == null) {
            showError("please select an enrollment to update.");
            return;
        }
        int id = update ? selectedEnrollment.getID() : 0;

        Enrollment enrollment = readForm(id);

        if (enrollment == null){
            return;
        }

        try {
            if (update) {
                CollegeDB.updateEnrollment(enrollment);
            } else {
                CollegeDB.addEnrollment(enrollment);
            }
            loadEnrollments();
            clearForm();
        } catch(SQLException e ){
            showError("Couldn't save enrollment: "+ e.getMessage());
        }
    }

    private Enrollment readForm(int id) {

        Student student = studentComboBox.getValue();
        Section section = sectionComboBox.getValue();
        String grade = gradeComboBox.getValue();

        if(student == null || section == null || grade ==null){
            showError("student ,section, and grade are required.");
            return null;
        }

        return new Enrollment(id, student.getID(), section.getID(),grade);
    }

    private void fillForm(Enrollment enrollment){

        updateEnrollmentButton.setDisable( enrollment == null);
        deleteEnrollmentButton.setDisable(enrollment == null);

        if (enrollment == null){
            return;
        }

        studentComboBox.setValue(findStudent(enrollment.getStudentID()));
        sectionComboBox.setValue(findSection(enrollment.getSectionID()));
        gradeComboBox.setValue(enrollment.getGrade());
    }

    private Student findStudent(int id){
        return studentComboBox.getItems().stream().filter(student -> student.getID() == id).findFirst().orElse(null);
    }

    private Section findSection(int id){
        return sectionComboBox.getItems().stream().filter(section -> section.getID() == id).findFirst().orElse(null);
    }

    private String describeSection(Section section) {

        return Stream.of(
                        "Section " + section.getID(),
                        "Course " + section.getCourseID(),
                        section.getSemester(),
                        section.getDay(),
                        section.getStartTime() == null ? null :
                                section.getStartTime() + "-" + section.getEndTime(),
                        section.getModality()
                )
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" | "));
    }

    private String describeEnrollment(Enrollment enrollment){
        Student student = findStudent(enrollment.getStudentID());
        Section section = findSection(enrollment.getSectionID());

        String studentName = student == null ? "Student ID: " + enrollment.getStudentID() : student.getFirstName() + " " + student.getLastName();

        String sectionName = section == null ? "Section ID: " + enrollment.getSectionID() : describeSection(section);

        return studentName + " | " + sectionName + " | Grade:" + Objects.toString(enrollment.getGrade(), "");
    }
    private void showError(String message){

        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
