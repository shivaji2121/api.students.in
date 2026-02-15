package com.api.student.in.services.sercviceImpl;

import com.api.student.in.dto.ApiResponse;
import com.api.student.in.dto.UserDto;
import com.api.student.in.dto.UserResponseDto;
import com.api.student.in.entity.UserEntity;
import com.api.student.in.handlers.EmailAlreadyExistsException;
import com.api.student.in.handlers.UserNotFoundException;
import com.api.student.in.repository.UserRepository;
import com.api.student.in.services.UserService;
import com.api.student.in.utils.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl  implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponseDto saveUser(UserDto userDto) {

        UserEntity userEntity = UserEntity.builder()
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phone(userDto.getPhone())
                .userStatus(UserStatus.ACTIVE)
                .dateOfBirth(userDto.getDateOfBirth())
                .build();

        UserEntity savedUser=this.userRepository.save(userEntity);

        return UserResponseDto.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .dateOfBirth(savedUser.getDateOfBirth())
                .userStatus(savedUser.getUserStatus())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .deletedAt(savedUser.getDeletedAt())
                .build();
    }

    @Override
    public UserResponseDto getUserById(Long userId) {

        UserEntity user=this.userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(()->new UserNotFoundException("user not found"));



        return mapToUserResponseDto(user);
    }

    @Override
    public List<UserEntity> getAllUsers() {
       List<UserEntity> allUsers= this.userRepository.findAll();

        return allUsers.stream().filter(user -> user.getDeletedAt()==null)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto updateUserById(UserDto userDto, Long userid) {
        UserEntity existingUser=this.userRepository.findById(userid).
                orElseThrow(()->new UserNotFoundException("user not found"));

        Optional<UserEntity> userWithEmail=userRepository.findByEmailAndDeletedAtIsNull(userDto.getEmail());

        if ((userWithEmail).isPresent() &&
                !userWithEmail.get().getId().equals(userid)) {

            throw new EmailAlreadyExistsException("Email already exists");
        }

        existingUser.setUserName(userDto.getUserName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPassword(userDto.getPassword());
        existingUser.setPhone(userDto.getPhone());
        existingUser.setUserStatus(userDto.getUserStatus());
        existingUser.setDateOfBirth(userDto.getDateOfBirth());

        UserEntity updatedUser=userRepository.save(existingUser);

        UserResponseDto userResponseDto=UserResponseDto.builder()
                .userName(updatedUser.getUserName())
                .email(updatedUser.getEmail())
                .phone(updatedUser.getPhone())
                .userStatus(updatedUser.getUserStatus())
                .dateOfBirth(updatedUser.getDateOfBirth())
                .build();
        return userResponseDto;
    }

    @Override
    public ApiResponse deleteUserById(Long id) {
        UserEntity user=this.userRepository.findById(id).
                orElseThrow(()->new UserNotFoundException("user not found"));

        user.setDeletedAt(LocalDateTime.now());
        user.setUserStatus(UserStatus.INACTIVE);
        userRepository.save(user);

        return new ApiResponse(false,"user deleted successfully",null);
    }

    private UserResponseDto mapToUserResponseDto(UserEntity user){
        return  UserResponseDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .dateOfBirth(user.getDateOfBirth())
                .userStatus(user.getUserStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deletedAt(user.getDeletedAt())
                .build();
   }


}
