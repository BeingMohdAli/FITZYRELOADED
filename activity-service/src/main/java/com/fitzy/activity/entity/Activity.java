package com.fitzy.activity.entity;

import com.fitzy.activity.enums.ActivityEnum;
import jakarta.persistence.*;
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
@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityEnum activity;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false)
    private Integer caloriesBurnt;

    @Column(nullable = false)
    private Instant startTime;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "activity_additional_metrics",
            joinColumns = @JoinColumn(name = "activity_id")
    )
    @MapKeyColumn(name = "metric_key")
    @Column(name = "metric_value")
    private Map<String, String> additionalMetrics = new HashMap<>();

    @Version
    @Column(nullable = false)
    private Long version;
}