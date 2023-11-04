package com.ghostflower.CA_System.exception;

import com.ghostflower.CA_System.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result HandleException(Exception e){
        e.printStackTrace();
        return Result.Error(StringUtils.hasLength(e.getMessage())?e.getMessage():"操作失败");


    }
}
