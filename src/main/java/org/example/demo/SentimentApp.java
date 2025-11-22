package org.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@SpringBootApplication
@RestController
public class SentimentApp {

    public static void main(String[] args) {
        SpringApplication.run(SentimentApp.class, args);
    }

    @GetMapping("/api/sentiment")
    public Map<String, String> analyze(@RequestParam(defaultValue = "") String text) {
        // Простейший Mock-анализ
        String sentiment = text.toLowerCase().contains("bad") ? "negative" : "positive";
        return Map.of("sentiment", sentiment, "input", text);
    }
}