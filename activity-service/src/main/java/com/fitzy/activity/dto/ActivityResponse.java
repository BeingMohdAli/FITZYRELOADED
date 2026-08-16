package com.fitzy.activity.dto;
import com.fitzy.activity.enums.ActivityEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
// TODO: record — what the API returns to the client
public class ActivityResponse  {

    private UUID id;

    private String userId;

    private ActivityEnum activity;

    private Integer durationMinutes;

    private Integer caloriesBurnt;

    private Instant startTime;

    private Instant createdAt;

    private Map<String, String> additionalMetrics = new HashMap<>();

}
