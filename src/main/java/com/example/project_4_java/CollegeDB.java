package com.example.project_4_java;

import java.sql.*;
import java.util.ArrayList;

public class CollegeDB {

    private static Connection getConnection() throws SQLException {
        String dbUrl = "jdbc:sqlite:college.db";
        Connection connection = DriverManager.getConnection(dbUrl);
        return connection;
    }

    public static void updateStudent(Student student){
        try {
            Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update student set FirstName = ?, LastName = ?, Major = ?, GPA = ? where ID = ?");

            // first value is index 1, it's ok to cry
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getMajor());
            preparedStatement.setDouble(4, student.getGpa());
            preparedStatement.setInt(5, student.getID());

            preparedStatement.execute();

            connection.close();
        } catch ( SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public static void updateCourse(Course course){
        try {
            Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update course set Department = ?, Number = ?, Title = ?, Credits = ? where ID = ?");

            // first value is index 1, it's ok to cry
            preparedStatement.setString(1, course.getDepartment());
            preparedStatement.setInt(2, course.getNumber());
            preparedStatement.setString(3, course.getTitle());
            preparedStatement.setInt(4, course.getCredits());
            preparedStatement.setInt(5, course.getID());

            preparedStatement.execute();

            connection.close();
        } catch ( SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public static void updateFaculty(Faculty faculty){
        try {
            Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update faculty set FirstName = ?, LastName = ?, Department = ? where ID = ?");

            // first value is index 1, it's ok to cry
            preparedStatement.setString(1, faculty.getFirstName());
            preparedStatement.setString(2, faculty.getLastName());
            preparedStatement.setString(3, faculty.getDepartment());
            preparedStatement.setInt(4, faculty.getID());

            preparedStatement.execute();

            connection.close();
        } catch ( SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public static ArrayList<Student> getStudent(int id){
        ArrayList<Student> students = new ArrayList<Student>();

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Student where id = ?");

            preparedStatement.setInt(1, id);

            ResultSet studentsQuery = preparedStatement.executeQuery();

            while (studentsQuery.next()) {
                String firstName = studentsQuery.getString("FirstName");
                String lastName = studentsQuery.getString("LastName");
                String major = studentsQuery.getString("Major");
                double gpa = studentsQuery.getDouble("GPA");

                students.add(new Student(id, firstName, lastName, major, gpa));
            }
            studentsQuery.close();

            connection.close();
        } catch ( SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }

        return students;
    }

    public static boolean deleteStudent(Student student){
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM Student where id = ?");

            preparedStatement.setInt(1, student.getID());

            preparedStatement.execute();


            preparedStatement.close();

            connection.close();

            return true;
        } catch ( SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<Student> getStudents(){
        ArrayList<Student> students = new ArrayList<Student>();

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet studentsQuery = statement.executeQuery(
                    "SELECT * FROM Student");

            while (studentsQuery.next()) {
                int id = studentsQuery.getInt("ID");
                String firstName = studentsQuery.getString("FirstName");
                String lastName = studentsQuery.getString("LastName");
                String major = studentsQuery.getString("Major");
                double gpa = studentsQuery.getDouble("GPA");

                students.add(new Student(id, firstName, lastName, major, gpa));
            }
            studentsQuery.close();

            connection.close();
        } catch ( SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }

        return students;
    }

    public static ArrayList<Course> getCourses(){
        ArrayList<Course> courses = new ArrayList<Course>();

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet coursesQuery = statement.executeQuery(
                    "SELECT * FROM Course");

            while (coursesQuery.next()) {
                int id = coursesQuery.getInt("ID");
                String department = coursesQuery.getString("Department");
                int number = coursesQuery.getInt("Number");
                String title = coursesQuery.getString("Title");
                int credits = coursesQuery.getInt("Credits");

                courses.add(new Course(id, department, number, title, credits));
            }
            coursesQuery.close();

            connection.close();
        } catch ( SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }

        return courses;
    }

    public static ArrayList<Faculty> getFaculties(){
        ArrayList<Faculty> faculties = new ArrayList<Faculty>();

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet facultiesQuery = statement.executeQuery(
                    "SELECT * FROM Faculty");

            while (facultiesQuery.next()) {
                int id = facultiesQuery.getInt("ID");
                String firstName = facultiesQuery.getString("FirstName");
                String lastName = facultiesQuery.getString("LastName");
                String department = facultiesQuery.getString("Department");

                faculties.add(new Faculty(id, firstName, lastName, department));
            }
            facultiesQuery.close();

            connection.close();
        } catch ( SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }

        return faculties;
    }

    public static void addStudent(Student student){
        try {
            Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT into Student ( FirstName, LastName, Major, GPA ) values ( ?, ?, ?, ? )");

            // first value is index 1, it's ok to cry
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getMajor());
            preparedStatement.setDouble(4, student.getGpa());

            preparedStatement.execute();

            connection.close();
        } catch ( SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public static void addCourse(Course course){
        try {
            Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT into Course ( Department, Number, Title, Credits ) values ( ?, ?, ?, ? )");

            // first value is index 1, it's ok to cry
            preparedStatement.setString(1, course.getDepartment());
            preparedStatement.setInt(2, course.getNumber());
            preparedStatement.setString(3, course.getTitle());
            preparedStatement.setInt(4, course.getCredits());

            preparedStatement.execute();

            connection.close();
        } catch ( SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public static void addFaculty(Faculty faculty){
        try {
            Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT into Faculty ( FirstName, LastName, Department) values ( ?, ?, ? )");

            // first value is index 1, it's ok to cry
            preparedStatement.setString(1, faculty.getFirstName());
            preparedStatement.setString(2, faculty.getLastName());
            preparedStatement.setString(3, faculty.getDepartment());

            preparedStatement.execute();

            connection.close();
        } catch ( SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public static ArrayList<Section> getSections() throws SQLException {
        ArrayList<Section> sections = new ArrayList<>();

        try (Connection connection = getConnection();
             ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Section")) {
            while (rs.next()) {
                sections.add(new Section(rs.getInt("ID"), rs.getInt("CourseID"), rs.getInt("FacultyID"),
                        rs.getString("Semester"), rs.getString("Day"), rs.getString("StartTime"),
                        rs.getString("EndTime"), rs.getString("Modality")));
            }
        }

        return sections;
    }

    public static void addSection(Section section) throws SQLException {
        saveSection(section, "INSERT into Section ( CourseID, FacultyID, Semester, Day, StartTime, EndTime, Modality ) values ( ?, ?, ?, ?, ?, ?, ? )");
    }

    public static void updateSection(Section section) throws SQLException {
        saveSection(section, "update Section set CourseID = ?, FacultyID = ?, Semester = ?, Day = ?, StartTime = ?, EndTime = ?, Modality = ? where ID = ?");
    }

    private static void saveSection(Section section, String sql) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, section.getCourseID());
            preparedStatement.setInt(2, section.getFacultyID());
            preparedStatement.setString(3, section.getSemester());
            preparedStatement.setString(4, section.getDay());
            preparedStatement.setString(5, section.getStartTime());
            preparedStatement.setString(6, section.getEndTime());
            preparedStatement.setString(7, section.getModality());
            if (sql.startsWith("update")) {
                preparedStatement.setInt(8, section.getID());
            }
            preparedStatement.execute();
        }
    }

}
