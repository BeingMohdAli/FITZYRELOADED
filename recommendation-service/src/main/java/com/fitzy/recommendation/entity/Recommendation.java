package com.fitzy.recommendation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "recommendations")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID activityId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String summary;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "recommendation_improvements",
            joinColumns = @JoinColumn(name = "recommendation_id")
    )
    @OrderColumn(name = "item_order")
    @Column(name = "improvement")
    private List<String> improvements = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "recommendation_suggestions",
            joinColumns = @JoinColumn(name = "recommendation_id")
    )
    @OrderColumn(name = "item_order")
    @Column(name = "suggestion")
    private List<String> suggestions = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "recommendation_safety_tips",
            joinColumns = @JoinColumn(name = "recommendation_id")
    )
    @OrderColumn(name = "item_order")
    @Column(name = "safety_tip")
    private List<String> safetyTips = new ArrayList<>();


    @Column(nullable = false, updatable = false)

    private Instant createdAt;

    @Version
    @Column(nullable = false)
    private Long version;

}
