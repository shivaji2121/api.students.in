package com.api.student.in.handlers;


import com.api.student.in.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class UserAPIHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {

        return new ResponseEntity<ResponseDto>(ResponseDto.builder()
                .errorMessages(
                        methodArgumentNotValidException.getBindingResult().getAllErrors()
                                .stream()
//                                .map(objectError -> objectError.getDefaultMessage())
                                .map(ObjectError::getDefaultMessage)
                                .collect(Collectors.toList())
                )
                .build(), HttpStatus.BAD_REQUEST);
    }
}
