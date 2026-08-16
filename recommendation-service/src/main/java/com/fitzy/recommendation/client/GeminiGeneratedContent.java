package com.fitzy.recommendation.client;

import java.util.List;

// The actual structured content we instruct Gemini to produce, inside that envelope's text field
public record GeminiGeneratedContent(
        String summary,
        List<String> improvements,
        List<String> suggestions,
        List<String> safetyTips
) {}