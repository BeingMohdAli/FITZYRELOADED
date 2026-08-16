package com.fitzy.activity.mapper;

import com.fitzy.activity.dto.ActivityRequest;
import com.fitzy.activity.dto.ActivityResponse;
import com.fitzy.activity.entity.Activity;
import org.mapstruct.Mapper;

// TODO: @Mapper(componentModel = "spring") — ActivityRequest -> Activity, Activity -> ActivityResponse
@Mapper(componentModel = "spring")
public interface ActivityMapper {
    Activity toEntity(ActivityRequest request);
    ActivityResponse toResponse(Activity activity);
}
