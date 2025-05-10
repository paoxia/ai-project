package com.example.ai.common.exception;

import java.sql.SQLSyntaxErrorException;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.ai.common.res.CommonResult;

import static com.example.ai.common.error.ResultCode.API_ERROR;
import static com.example.ai.common.error.ResultCode.BIZ_ERROR;
import static com.example.ai.common.error.ResultCode.VALIDATE_FAILED;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(ApiException e) {
        return CommonResult.failed(API_ERROR.getCode(), e.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(value = BizException.class)
    public CommonResult handle(BizException e) {
        return CommonResult.failed(BIZ_ERROR.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = ParamException.class)
    public CommonResult handle(ParamException e) {
        return CommonResult.failed(VALIDATE_FAILED.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (Objects.nonNull(fieldError)) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }


    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public CommonResult handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (Objects.nonNull(fieldError)) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }


    @ResponseBody
    @ExceptionHandler(value = SQLSyntaxErrorException.class)
    public CommonResult handleSQLSyntaxErrorException(SQLSyntaxErrorException e) {
        String message = e.getMessage();
        if (StringUtils.isNotEmpty(message) && message.contains("denied")) {
            message = "数据访问失败";
        }
        return CommonResult.failed(message);
    }

}
