package com.fitzy.activity.dto;

import com.fitzy.activity.enums.ActivityEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

// TODO: record — @NotNull/@Min/@Max validated fields, NO userId field (always taken from auth later)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRequest {

    @NotNull
    private ActivityEnum activity;

    @NotNull
    @Positive
    private Integer durationMinutes;

    @NotNull
    @PositiveOrZero
    private Integer caloriesBurnt;

    @NotNull
    private Instant startTime;

    private Map<String, String> additionalMetrics = new HashMap<>();;


}
