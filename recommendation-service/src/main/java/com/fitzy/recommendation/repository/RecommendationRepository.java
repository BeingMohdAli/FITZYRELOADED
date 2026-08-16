package com.fitzy.recommendation.repository;

import com.fitzy.recommendation.entity.Recommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRepository extends JpaRepository<Recommendation, UUID> {

    Optional<Recommendation> findByActivityId(UUID activityId);

    Page<Recommendation> findByUserId(String userId, Pageable pageable);
}