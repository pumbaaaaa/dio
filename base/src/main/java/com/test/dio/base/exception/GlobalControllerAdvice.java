package com.test.dio.base.exception;

import com.test.dio.base.res.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(AppBusinessException.class)
    @ResponseBody
    public ResponseData saveModuConfException(AppBusinessException e) {
        return ResponseData.builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .message(e.getMessage())
                .build();
    }
}
