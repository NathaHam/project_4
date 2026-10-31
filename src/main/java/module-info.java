module com.example.project_4_java {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.project_4_java to javafx.fxml;
    exports com.example.project_4_java;
}