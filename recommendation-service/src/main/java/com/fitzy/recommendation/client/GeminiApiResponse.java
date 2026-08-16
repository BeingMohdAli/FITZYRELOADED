package com.fitzy.recommendation.client;

import java.util.List;

// Raw envelope Gemini's API always wraps responses in, regardless of what content we asked for
record GeminiApiResponse(List<Candidate> candidates) {
    record Candidate(Content content) {}
    record Content(List<Part> parts) {}
    record Part(String text) {}
}