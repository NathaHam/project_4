package com.example.project_4_java;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FacultyController {

    @FXML
    private ListView<Faculty> facultyListView;
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField departmentTextField;
    @FXML private Button updateFacultyButton;

    public void initialize(){
        loadFaculties();
    }

    @FXML
    protected void loadFaculties() {
        facultyListView.getItems().setAll(CollegeDB.getFaculties());
    }

    @FXML
    protected void updateFacultyButtonClicked() {
        save(true);
    }

    @FXML
    protected void addFacultyButtonClicked() {
        save(false);
    }

    @FXML
    protected void clearForm() {
        facultyListView.getSelectionModel().clearSelection();
        firstNameTextField.clear();
        lastNameTextField.clear();
        departmentTextField.clear();
        updateFacultyButton.setDisable(true);
    }

    @FXML
    protected void facultySelected(){

        Faculty selectedFaculty = facultyListView.getSelectionModel().getSelectedItem();

        if(selectedFaculty != null){
            fillForm(selectedFaculty);
        }
    }

    private void fillForm(Faculty faculty) {
        updateFacultyButton.setDisable(faculty == null);
        if (faculty == null) {
            return;
        }

        firstNameTextField.setText(faculty.getFirstName());
        lastNameTextField.setText(faculty.getLastName());
        departmentTextField.setText(faculty.getDepartment());
    }

    private void save(boolean update) {

        int id;

        if(update){
            id = facultyListView.getSelectionModel().getSelectedItem().getID();
        }
        else{
            id = 0;
        }

        Faculty faculty = readForm(id);
        if (faculty == null) {
            return;
        }

        if (update) {
            CollegeDB.updateFaculty(faculty);
        } else {
            CollegeDB.addFaculty(faculty);
        }
        loadFaculties();
        clearForm();
    }

    private Faculty readForm(int id) {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String department = departmentTextField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || department.isEmpty()) {
            showError("First name, last name, and department are required.");
            return null;
        }

        return new Faculty(id, firstName, lastName, department);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
