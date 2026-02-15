package com.api.student.in.controllers;


import com.api.student.in.dto.ApiResponse;
import com.api.student.in.dto.ResponseDto;
import com.api.student.in.dto.UserDto;
import com.api.student.in.dto.UserResponseDto;
import com.api.student.in.entity.UserEntity;
import com.api.student.in.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/save")
    public ResponseEntity<UserResponseDto> saveUser(@Valid @RequestBody UserDto userDto){

        UserResponseDto user=userService.saveUser(userDto);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    };

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId){

        UserResponseDto user=this.userService.getUserById(userId);

        return ResponseEntity.ok(user);
    }

    @GetMapping(path = "/all")
    public  ResponseEntity<List<UserEntity>> getAllUsers(){
        List<UserEntity>  list=this.userService.getAllUsers();
        return  ResponseEntity.ok(list);
    }

    @PutMapping(path = "/{userId}")
    public  ResponseEntity<UserResponseDto> updateUserById(@RequestBody UserDto userDto,@PathVariable Long userId){
        UserResponseDto updatedUser=this.userService.updateUserById(userDto,userId);
        return  ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping(path = "/{id}")
    public  ResponseEntity<ApiResponse> deleteUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUserById(id));
    }
}
