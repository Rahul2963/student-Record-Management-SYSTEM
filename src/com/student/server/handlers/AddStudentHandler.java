package com.student.server.handlers;

import com.student.dao.StudentDAO;
import com.student.model.Student;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.sql.SQLException;

public class AddStudentHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            // Read request body
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(exchange.getRequestBody()));
            Student student = new Gson().fromJson(reader, Student.class);

            // Save to database
            boolean success = false;
            try {
                success = new StudentDAO().addStudent(student);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Send response
            String response = "{\"success\": " + success + "}";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}