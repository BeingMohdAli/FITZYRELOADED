package com.fitzy.recommendation.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitzy.common.event.ActivityTrackedEvent;
import com.fitzy.common.exception.ExternalServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GeminiClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final String apiKey;
    private final String model;

    public GeminiClient(RestClient.Builder restClientBuilder,
                        ObjectMapper objectMapper,
                        @Value("${gemini.base-url}") String baseUrl,
                        @Value("${gemini.api-key}") String apiKey,
                        @Value("${gemini.model}") String model) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
        this.objectMapper = objectMapper;
        this.apiKey = apiKey;
        this.model = model;
    }

    public GeminiGeneratedContent generateRecommendation(ActivityTrackedEvent event) {
        String prompt = buildPrompt(event);
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of("parts", List.of(Map.of("text", prompt)))),
                "generationConfig", Map.of("responseMimeType", "application/json")
        );

        int maxAttempts = 3;
        RestClientException lastError = null;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                GeminiApiResponse response = restClient.post()
                        .uri("/models/{model}:generateContent", model)
                        .header("x-goog-api-key", apiKey)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(requestBody)
                        .retrieve()
                        .body(GeminiApiResponse.class);

                String rawJsonText = response.candidates().get(0).content().parts().get(0).text();
                return objectMapper.readValue(rawJsonText, GeminiGeneratedContent.class);

            } catch (RestClientException e) {
                lastError = e;
                log.warn("Gemini call failed (attempt {}/{}): {}", attempt, maxAttempts, e.getMessage());
                if (attempt < maxAttempts) {
                    try {
                        Thread.sleep(1500L * attempt); // 1.5s, then 3s backoff
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            } catch (Exception e) {
                // Malformed response shape — retrying won't help, fail fast
                throw new ExternalServiceException("Failed to parse Gemini response", lastError);
            }
        }

        throw new ExternalServiceException("Gemini API unavailable after " + maxAttempts + " attempts",lastError);
    }

    private String buildPrompt(ActivityTrackedEvent event) {
        return """
                You are a fitness coach AI. Analyze this workout and respond with ONLY a JSON object
                matching exactly this shape, no other text:
                {
                  "summary": "one or two sentence overall assessment",
                  "improvements": ["2-4 specific things to improve about this workout"],
                  "suggestions": ["2-4 forward-looking suggestions for future training"],
                  "safetyTips": ["1-3 relevant safety or recovery tips"]
                }

                Activity type: %s
                Duration: %d minutes
                Calories burnt: %d
                Additional metrics: %s
                """.formatted(
                event.activityType(),
                event.durationMinutes(),
                event.caloriesBurnt(),
                event.additionalMetrics()
        );
    }
}