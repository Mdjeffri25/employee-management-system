package com.hightech.ems.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    @PostMapping("/ask")
    public Map<String, String> askChatbot(@RequestBody Map<String, String> request) {
        String question = request.get("question").toLowerCase();
        String answer = "I'm sorry, I didn't understand that. I am a high-tech HR assistant. Ask me about employees or policies.";

        if (question.contains("hello") || question.contains("hi")) {
            answer = "Hello! tailored to assist you efficiently. How can I help you today?";
        } else if (question.contains("employee")) {
            answer = "You can view, add, update, or delete employees from the dashboard. Navigate to the main list to see all records.";
        } else if (question.contains("policy") || question.contains("hr")) {
            answer = "Our HR policy prioritizes employee well-being. For specific documents, please check the internal portal or ask your manager.";
        } else if (question.contains("salary")) {
            answer = "Salary information is confidential and available only to authorized HR personnel.";
        } else if (question.contains("tech")) {
            answer = "This system is built with Spring Boot and high-tech styling for maximum performance and aesthetics.";
        }

        Map<String, String> response = new HashMap<>();
        response.put("answer", answer);
        return response;
    }
}
