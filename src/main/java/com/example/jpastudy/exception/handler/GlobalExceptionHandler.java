package com.example.jpastudy.exception.handler;

import com.example.jpastudy.exception.ErrorCode;
import com.example.jpastudy.exception.ErrorResponse;
import com.example.jpastudy.exception.custom.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;


@Slf4j
@RestControllerAdvice       // 모든 rest 컨트롤러에서 발생하는 예외처리
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex){
        log.error("handleCustomException",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        log.error("handleException",ex);
        if(ex instanceof HttpClientErrorException.BadRequest){          // 400
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } else if(ex instanceof HttpClientErrorException.Unauthorized){ // 401
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        } else if(ex instanceof HttpClientErrorException.Forbidden){    // 403
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.FORBIDDEN), HttpStatus.FORBIDDEN);
        } else if(ex instanceof HttpClientErrorException.NotFound){     // 404
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND), HttpStatus.NOT_FOUND);
        }else {                                                         // 500
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTER_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

