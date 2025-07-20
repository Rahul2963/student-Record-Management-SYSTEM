package com.student.server.handlers;

import com.student.dao.StudentDAO;
import com.google.gson.Gson;
import com.student.model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class GetStudentsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            // Get all students
            List<Student> students = null;
            try {
                students = new StudentDAO().getAllStudents();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Convert to JSON
            String response = new Gson().toJson(students);

            // Send response
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}