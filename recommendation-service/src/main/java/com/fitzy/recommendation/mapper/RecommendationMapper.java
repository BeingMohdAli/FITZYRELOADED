package com.fitzy.recommendation.mapper;

import com.fitzy.recommendation.dto.RecommendationRequest;
import com.fitzy.recommendation.dto.RecommendationResponse;
import com.fitzy.recommendation.entity.Recommendation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface RecommendationMapper {
    RecommendationResponse toResponse(Recommendation recommendation);
//    Recommendation toEntity(RecommendationRequest recommendationRequest);
}
