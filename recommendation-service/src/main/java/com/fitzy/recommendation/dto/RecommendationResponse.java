package com.fitzy.recommendation.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecommendationResponse {

    private UUID id;
    private UUID activityId;
    private String userId;
    private String summary;
    private List<String> improvements = new ArrayList<>();
    private List<String> suggestions = new ArrayList<>();
    private List<String> safetyTips = new ArrayList<>();
    private Instant createdAt;



}
