package com.example.project_4_java;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SectionController {

    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("H:mm");

    @FXML private ListView<Section> sectionListView;
    @FXML private ComboBox<Course> courseComboBox;
    @FXML private ComboBox<Faculty> facultyComboBox;
    @FXML private TextField semesterTextField;
    @FXML private TextField dayTextField;
    @FXML private TextField startTimeTextField;
    @FXML private TextField endTimeTextField;
    @FXML private ComboBox<String> modalityComboBox;
    @FXML private Button updateSectionButton;

    public void initialize() {
        modalityComboBox.getItems().addAll("In Person", "Online", "Hybrid");
        courseComboBox.setConverter(converter(c -> c.getDepartment() + " " + c.getNumber() + " - " + c.getTitle()));
        facultyComboBox.setConverter(converter(f -> f.getFirstName() + " " + f.getLastName()));

        sectionListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Section section, boolean empty) {
                super.updateItem(section, empty);
                setText(empty || section == null ? null : describe(section));
            }
        });
        sectionListView.getSelectionModel().selectedItemProperty().addListener((obs, old, section) -> fillForm(section));

        loadSections();
    }

    @FXML
    protected void loadSections() {
        courseComboBox.getItems().setAll(CollegeDB.getCourses());
        facultyComboBox.getItems().setAll(CollegeDB.getFaculties());
        try {
            sectionListView.getItems().setAll(CollegeDB.getSections());
        } catch (SQLException e) {
            showError("Couldn't load sections: " + e.getMessage());
        }
    }

    @FXML
    protected void addSectionButtonClicked() {
        save(false);
    }

    @FXML
    protected void updateSectionButtonClicked() {
        save(true);
    }

    @FXML
    protected void clearForm() {
        sectionListView.getSelectionModel().clearSelection();
        courseComboBox.setValue(null);
        facultyComboBox.setValue(null);
        modalityComboBox.setValue(null);
        semesterTextField.clear();
        dayTextField.clear();
        startTimeTextField.clear();
        endTimeTextField.clear();
    }

    private void save(boolean update) {
        int id = update ? sectionListView.getSelectionModel().getSelectedItem().getID() : 0;
        Section section = readForm(id);
        if (section == null) {
            return;
        }

        try {
            if (update) {
                CollegeDB.updateSection(section);
            } else {
                CollegeDB.addSection(section);
            }
            loadSections();
            clearForm();
        } catch (SQLException e) {
            showError("Couldn't save section: " + e.getMessage());
        }
    }

    private Section readForm(int id) {
        Course course = courseComboBox.getValue();
        Faculty faculty = facultyComboBox.getValue();
        String modality = modalityComboBox.getValue();
        String semester = semesterTextField.getText().trim();
        String day = dayTextField.getText().trim().toUpperCase();
        String start = startTimeTextField.getText().trim();
        String end = endTimeTextField.getText().trim();

        if (course == null || faculty == null || modality == null || semester.isEmpty()) {
            showError("Course, faculty, semester, and modality are required.");
            return null;
        }
        if (start.isEmpty() != end.isEmpty()) {
            showError("Enter both a start and end time, or leave both blank.");
            return null;
        }
        if (!start.isEmpty()) {
            try {
                LocalTime startTime = LocalTime.parse(start, TIME);
                LocalTime endTime = LocalTime.parse(end, TIME);
                if (!endTime.isAfter(startTime)) {
                    showError("End time has to be after the start time.");
                    return null;
                }
                start = startTime.toString();
                end = endTime.toString();
            } catch (DateTimeParseException e) {
                showError("Times should look like 9:30 or 14:00.");
                return null;
            }
        }

        return new Section(id, course.getID(), faculty.getID(), semester, blankToNull(day), blankToNull(start), blankToNull(end), modality);
    }

    private void fillForm(Section section) {
        updateSectionButton.setDisable(section == null);
        if (section == null) {
            return;
        }

        courseComboBox.setValue(find(courseComboBox, Course::getID, section.getCourseID()));
        facultyComboBox.setValue(find(facultyComboBox, Faculty::getID, section.getFacultyID()));
        modalityComboBox.setValue(section.getModality());
        semesterTextField.setText(section.getSemester());
        dayTextField.setText(Objects.toString(section.getDay(), ""));
        startTimeTextField.setText(Objects.toString(section.getStartTime(), ""));
        endTimeTextField.setText(Objects.toString(section.getEndTime(), ""));
    }

    private String describe(Section section) {
        Course course = find(courseComboBox, Course::getID, section.getCourseID());
        Faculty faculty = find(facultyComboBox, Faculty::getID, section.getFacultyID());
        String time = section.getStartTime() == null ? null : section.getStartTime() + "-" + section.getEndTime();

        return Stream.of(
                        course == null ? "Course " + section.getCourseID() : course.getDepartment() + " " + course.getNumber(),
                        section.getSemester(),
                        section.getDay(),
                        time,
                        section.getModality(),
                        faculty == null ? "Faculty " + section.getFacultyID() : faculty.getFirstName() + " " + faculty.getLastName())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" | "));
    }

    private <T> T find(ComboBox<T> comboBox, ToIntFunction<T> getID, int id) {
        return comboBox.getItems().stream().filter(item -> getID.applyAsInt(item) == id).findFirst().orElse(null);
    }

    private <T> StringConverter<T> converter(Function<T, String> toText) {
        return new StringConverter<>() {
            @Override
            public String toString(T item) {
                return item == null ? "" : toText.apply(item);
            }

            @Override
            public T fromString(String text) {
                return null;
            }
        };
    }

    private String blankToNull(String text) {
        return text.isEmpty() ? null : text;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
