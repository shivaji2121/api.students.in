package com.api.student.in.services;

import com.api.student.in.dto.ApiResponse;
import com.api.student.in.dto.ResponseDto;
import com.api.student.in.dto.UserDto;
import com.api.student.in.dto.UserResponseDto;
import com.api.student.in.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponseDto saveUser(UserDto userDto);


    UserResponseDto getUserById(Long userId);

    List<UserEntity> getAllUsers();


    UserResponseDto updateUserById(UserDto userDto,Long userid);

    ApiResponse deleteUserById(Long id);
}
