package com.colirx.jieax.common.handler;

import com.colirx.jieax.JieaxBackendApplication;
import com.colirx.jieax.common.pojo.ResultCode;
import com.colirx.jieax.common.pojo.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<String> exception(Exception e) {
        log.error(String.format("全局异常: %s", e.getMessage()), e);
        return ResultData.failure(ResultCode.RC_500.getCode(), e.getMessage());
    }
}
