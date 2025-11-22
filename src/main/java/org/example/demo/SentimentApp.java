package org.example.demo;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
public class SentimentApp {

    private final Counter positiveCounter;
    private final Counter negativeCounter;

    // Micrometer автоматически внедряет MeterRegistry
    public SentimentApp(MeterRegistry registry) {
        // Создаем счетчики с префиксом 'sentiment_requests_total'
        this.positiveCounter = registry.counter("sentiment_requests_total", "result", "positive");
        this.negativeCounter = registry.counter("sentiment_requests_total", "result", "negative");
    }

    public static void main(String[] args) {
        SpringApplication.run(SentimentApp.class, args);
    }

    @GetMapping("/api/sentiment")
    public Map<String, String> analyze(@RequestParam(defaultValue = "") String text) {
        String sentiment = text.toLowerCase().contains("bad") ? "negative" : "positive";

        // Увеличиваем соответствующий счетчик
        if ("negative".equals(sentiment)) {
            negativeCounter.increment();
        } else {
            positiveCounter.increment();
        }

        return Map.of("sentiment", sentiment, "input", text);
    }
}