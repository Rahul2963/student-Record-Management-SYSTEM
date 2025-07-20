package com.student;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.student.server.handlers.AddStudentHandler;
import com.student.server.handlers.GetStudentsHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class Main {
    public static void main(String[] args) {
        try {
            // Create HTTP server
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            // Register API routes
            server.createContext("/add-student", new AddStudentHandler());
            server.createContext("/get-students", new GetStudentsHandler());

            // Serve static files like index.html, script.js, style.css
            server.createContext("/", exchange -> {
                String path = exchange.getRequestURI().getPath();

                if (path.equals("/")) {
                    path = "/index.html";
                }

                try {
                    byte[] response = Files.readAllBytes(Paths.get("src/web" + path));
                    String contentType = getContentType(path);
                    exchange.getResponseHeaders().set("Content-Type", contentType);
                    exchange.sendResponseHeaders(200, response.length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response);
                    os.close();
                } catch (IOException e) {
                    String notFound = "404 Not Found";
                    exchange.sendResponseHeaders(404, notFound.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(notFound.getBytes());
                    os.close();
                }
            });

            server.setExecutor(null); // default executor
            server.start();
            System.out.println("Server started on http://localhost:8000");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getContentType(String path) {
        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".css")) return "text/css";
        return "text/plain";
    }
}