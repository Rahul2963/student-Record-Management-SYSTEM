package com.student.dao;
import com.student.model.Student;
import com.student.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public boolean addStudent(Student student) throws SQLException{
        String query = "INSERT INTO students (name,email,department,grade) VALUES (?,?,?,?)";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt= conn.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setString(2,student.getEmail());
            stmt.setString(3,student.getDepartment());
            stmt.setFloat(4,student.getGrade());
            return stmt.executeUpdate()>0;
        }
    }

    public List<Student> getAllStudents() throws SQLException{
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try(Connection conn = DBUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Student student= new Student(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("department"),
                        rs.getFloat("grade")
                );
                student.setId(rs.getInt("id"));
                students.add(student);
            }
        }
        return students;
    }

    public boolean updateStudent(Student student) throws SQLException{
        String query = "UPDATE students SET name=?, email=?, department=?, grade=? WHERE id=?";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt= conn.prepareStatement(query)) {
            stmt.setString(1,student.getName());
            stmt.setString(1,student.getEmail());
            stmt.setString(1,student.getDepartment());
            stmt.setFloat(1,student.getGrade());
            stmt.setInt(1,student.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteStudent(int id) throws SQLException{
        String query = "DELETE FROM students WHERE Id=?";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1,id);
            return stmt.executeUpdate()>0;
        }
    }
}
