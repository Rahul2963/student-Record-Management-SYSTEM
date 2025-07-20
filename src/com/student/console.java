package com.student;
import com.student.dao.StudentDAO;
import com.student.model.Student;
import java.util.Scanner;
import java.util.List;

public class console {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentDAO studentDAO = new StudentDAO();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewAllStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Student Management System ===");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Exit");
    }

    private static void addStudent() {
        System.out.println("\n--- Add New Student ---");
        String name = getStringInput("Name: ");
        String email = getStringInput("Email: ");
        String department = getStringInput("Department: ");
        float grade = getFloatInput("Grade: ");

        try {
            Student student = new Student(name, email, department, grade);
            boolean success = studentDAO.addStudent(student);
            System.out.println(success ? "Student added successfully!" : "Failed to add student.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAllStudents() {
        try {
            List<Student> students = studentDAO.getAllStudents();
            System.out.println("\n--- All Students ---");
            System.out.printf("%-5s %-20s %-25s %-15s %-5s\n",
                    "ID", "Name", "Email", "Department", "Grade");

            for (Student s : students) {
                System.out.printf("%-5d %-20s %-25s %-15s %-5.1f\n",
                        s.getId(), s.getName(), s.getEmail(), s.getDepartment(), s.getGrade());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateStudent() {
        viewAllStudents();
        int id = getIntInput("\nEnter student ID to update: ");

        try {
            Student existing = studentDAO.getAllStudents().stream()
                    .filter(s -> s.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new Exception("Student not found"));

            System.out.println("\nCurrent details:");
            System.out.println(existing);

            Student updated = new Student(
                    getStringInput("New name [" + existing.getName() + "]: ", existing.getName()),
                    getStringInput("New email [" + existing.getEmail() + "]: ", existing.getEmail()),
                    getStringInput("New department [" + existing.getDepartment() + "]: ", existing.getDepartment()),
                    getFloatInput("New grade [" + existing.getGrade() + "]: ", existing.getGrade())
            );
            updated.setId(id);

            boolean success = studentDAO.updateStudent(updated);
            System.out.println(success ? "Update successful!" : "Update failed.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteStudent() {
        viewAllStudents();
        int id = getIntInput("\nEnter student ID to delete: ");

        try {
            boolean success = studentDAO.deleteStudent(id);
            System.out.println(success ? "Student deleted!" : "Deletion failed.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Utility methods for input handling
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static String getStringInput(String prompt, String defaultValue) {
        String input = getStringInput(prompt);
        return input.isEmpty() ? defaultValue : input;
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(getStringInput(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private static float getFloatInput(String prompt) {
        while (true) {
            try {
                return Float.parseFloat(getStringInput(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private static float getFloatInput(String prompt, float defaultValue) {
        String input = getStringInput(prompt);
        return input.isEmpty() ? defaultValue : Float.parseFloat(input);
    }
}

