// src/main/java/com/example/studentqa/StudentQaServer.java
package example.studentqa;

import com.sun.net.httpserver.HttpServer;
import example.studentqa.handler.QuestionHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class StudentQaServer {
    private static final int PORT = 8080; 
    private static final String CONTEXT_PATH = "/sanjana-tds-project1";

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            QuestionHandler questionHandler = new QuestionHandler();
            server.createContext(CONTEXT_PATH, questionHandler);
            server.setExecutor(null); 
            server.start();

            System.out.println("Student QA Server started on port " + PORT);
            System.out.println("Access API at http://localhost:" + PORT + CONTEXT_PATH);

        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
