package com.api.student.in.dto;

import com.api.student.in.utils.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Builder
public class UserDto {

    @NotBlank(message = "User name is required")
    @Size(min = 2,max = 50,message = "Username must be between 3 and 50 characters")
    private String userName;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private  String email;

    @NotBlank(message = "Password is required")
    private  String password;


    @NotBlank(message = "phone number is required")
    private String phone;

    private UserStatus userStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;

}
