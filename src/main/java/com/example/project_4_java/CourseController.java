package com.example.project_4_java;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CourseController {

    @FXML private ListView<Course> courseListView;
    @FXML private TextField courseTextField;
    @FXML private TextField departmentTextField;
    @FXML private TextField numberTextField;
    @FXML private TextField creditsTextField;
    @FXML private Button updateCourseButton;

    public void initialize(){
        loadCourses();
    }

    @FXML
    protected void loadCourses() {
        courseListView.getItems().setAll(CollegeDB.getCourses());
    }

    @FXML
    protected void updateCourseButtonClicked() {
        save(true);
    }

    @FXML
    protected void addCourseButtonClicked() {
        save(false);
    }

    @FXML
    protected void clearForm() {
        courseListView.getSelectionModel().clearSelection();
        courseTextField.clear();
        departmentTextField.clear();
        numberTextField.clear();
        creditsTextField.clear();
        updateCourseButton.setDisable(true);
    }

    @FXML
    protected void courseSelected(){

        Course selectedCourse = courseListView.getSelectionModel().getSelectedItem();

        if(selectedCourse != null){
            fillForm(selectedCourse);
        }
    }

    private void fillForm(Course course) {
        updateCourseButton.setDisable(course == null);
        if (course == null) {
            return;
        }

        courseTextField.setText(course.getTitle());
        departmentTextField.setText(course.getDepartment());
        numberTextField.setText("" + course.getNumber());
        creditsTextField.setText("" + course.getCredits());
    }


    private void save(boolean update) {

        int id;
        
        if(update){
            id = courseListView.getSelectionModel().getSelectedItem().getID();
        }
        else{
            id = 0;
        }

        Course course = readForm(id);
        if (course == null) {
            return;
        }

        if (update) {
            CollegeDB.updateCourse(course);
        } else {
            CollegeDB.addCourse(course);
        }
        loadCourses();
        clearForm();
    }

    private Course readForm(int id) {
        String title = courseTextField.getText();
        String department = departmentTextField.getText();
        String numberString = numberTextField.getText().trim();
        String creditsString = creditsTextField.getText().trim();
        int number;
        int credits;

        if (title.isEmpty() || department.isEmpty() || numberString.isEmpty() || creditsString.isEmpty()) {
            showError("Title, department, number, and credits are required.");
            return null;
        }

        try{
            number = Integer.parseInt(numberString);
            credits = Integer.parseInt(creditsString);
        } catch (NumberFormatException e){
            showError("Number and credits have to be integers");
            return null;
        }

        return new Course(id, department, number, title, credits);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
