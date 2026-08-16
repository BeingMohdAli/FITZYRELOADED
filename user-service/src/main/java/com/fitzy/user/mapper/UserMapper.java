package com.fitzy.user.mapper;


import com.fitzy.user.dto.UserRequest;
import com.fitzy.user.dto.UserResponse;
import com.fitzy.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest userRequest);
    UserResponse toResponse(User user);
}
