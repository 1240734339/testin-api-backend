package cn.testin.commons.handler;

import cn.testin.commons.Response.ResponseResult;
import cn.testin.commons.exception.TIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author WangG
 * @title: ExceptionHandler
 * @description: 异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 自定义异常处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(TIException.class)
    public ResponseResult<?> handleGeneralException(TIException e) {
        log.error(e.getMessage());
        return ResponseResult.error(e.getCode(), e.getMessage());

    }

}
