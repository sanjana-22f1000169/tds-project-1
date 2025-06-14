package example.studentqa.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.studentqa.model.StudentQuestionRequest;
import example.studentqa.model.StudentAnswerResponse;
import example.studentqa.model.Attachment;
import example.studentqa.model.Link;
import example.studentqa.model.KnowledgeEntry; 

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionHandler implements HttpHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, KnowledgeEntry> knowledgeBase = new HashMap<>();

    public QuestionHandler() {
        loadKnowledgeBase();
    }

    private void loadKnowledgeBase() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("knowledge.json")) {
            if (is == null) {
                System.err.println("knowledge.json not found in classpath. Starting with empty knowledge base.");
                return;
            }

            StringBuilder jsonContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line);
                }
            }
            List<KnowledgeEntry> entries = objectMapper.readValue(jsonContent.toString(), new TypeReference<List<KnowledgeEntry>>() {});

            for (KnowledgeEntry entry : entries) {
                if (entry.getQuestion() != null && !entry.getQuestion().trim().isEmpty() && entry.getAnswers() != null) {
                    
                    knowledgeBase.put(entry.getQuestion().toLowerCase(), entry);
                }
            }
            System.out.println("Knowledge base loaded successfully from knowledge.json. Loaded " + knowledgeBase.size() + " entries.");
        } catch (IOException e) {
            System.err.println("Error loading knowledge base from JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        
        if (!"POST".equals(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "Method Not Allowed");
            return;
        }

        try (InputStream requestBody = exchange.getRequestBody()) {
            
            byte[] bytes = requestBody.readAllBytes();
            String requestJson = new String(bytes, StandardCharsets.UTF_8);

            StudentQuestionRequest request;
            try {
                
                request = objectMapper.readValue(requestJson, StudentQuestionRequest.class);
            } catch (Exception e) {
                
                sendResponse(exchange, 400, "Invalid JSON format: " + e.getMessage());
                return;
            }

            if (request == null || request.getQuestion() == null || request.getQuestion().trim().isEmpty()) {
                
                sendResponse(exchange, 400, objectMapper.writeValueAsString(new StudentAnswerResponse("Please provide a question.", null)));
                return;
            }

            String studentQuestion = request.getQuestion().trim().toLowerCase();
            KnowledgeEntry entry = knowledgeBase.get(studentQuestion); 

            String answerText;
            List<Link> links = new ArrayList<>();

            if (entry != null) {
                
                if (entry.getAnswers() != null && !entry.getAnswers().isEmpty()) {
                    if (entry.getAnswers().size() == 1) {
                        answerText = entry.getAnswers().get(0);
                    } else {
                        
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < entry.getAnswers().size(); i++) {
                            sb.append(i + 1).append(". ").append(entry.getAnswers().get(i)).append("\n");
                        }
                        answerText = sb.toString().trim(); 
                    }
                } else {
                    answerText = "I'm sorry, I don't have an answer for that specific question in my current knowledge base.";
                }

                
                if (entry.getLinks() != null) {
                    links.addAll(entry.getLinks());
                }
            } else {
                answerText = "I'm sorry, I don't have an answer for that specific question in my current knowledge base.";
            }
            
            if (request.getAttachments() != null && !request.getAttachments().isEmpty()) {
                StringBuilder attachmentInfo = new StringBuilder("\n\nNote: I received ");
                attachmentInfo.append(request.getAttachments().size()).append(" attachment(s): ");
                for (int i = 0; i < request.getAttachments().size(); i++) {
                    Attachment attachment = request.getAttachments().get(i);
                    attachmentInfo.append(attachment.getFileName());
                    if (i < request.getAttachments().size() - 1) {
                        attachmentInfo.append(", ");
                    }
                }
                attachmentInfo.append(". My current system does not process attachments directly for answering questions.");
                answerText += attachmentInfo.toString();
            }
            
            if (request.getImage() != null && !request.getImage().trim().isEmpty()) {
                String imageInfo = "\n\nNote: I also received an image. My current system does not process images directly for answering questions.";
                answerText += imageInfo;
            }

            StudentAnswerResponse response = new StudentAnswerResponse(answerText, links);
            
            String jsonResponse = objectMapper.writeValueAsString(response);
            
            sendResponse(exchange, 200, jsonResponse);

        } catch (IOException e) {
            System.err.println("Error processing request: " + e.getMessage());
            e.printStackTrace();
            sendResponse(exchange, 500, "Internal Server Error");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String responseBody) throws IOException {
        byte[] responseBytes = responseBody.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
