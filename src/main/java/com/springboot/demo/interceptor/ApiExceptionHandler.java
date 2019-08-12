package com.springboot.demo.interceptor;

import com.springboot.demo.util.RuleEngineException;
import com.springboot.demo.vo.ResponseBean;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler
    @ResponseBody
    public ResponseBean exceptionHandler(Exception ex) {
        ResponseBean result=new ResponseBean();

        if (ex instanceof BindException) {
            List<FieldError> allErrors = ((BindException) ex).getFieldErrors();

            return result.errorResult(allErrors.stream().map(error -> error.getField() + error.getDefaultMessage()).collect(Collectors.joining(",")));
        } else if (ex instanceof MethodArgumentNotValidException) {
            List<FieldError> allErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors();
            return result.errorResult(allErrors.stream().map(error -> error.getField() + error.getDefaultMessage()).collect(Collectors.joining(",")));
        } else if (ex instanceof ValidationException) {
            ValidationException ex1 = (ValidationException) ex;
            result.setMessage(ex1.getMessage());
            return result;
        }else if(ex instanceof RuleEngineException){
            /**
             * 规则引擎异常处理专用，主要是前端需要返回000000的message
             */
            RuleEngineException ex1 = (RuleEngineException) ex;
            result.setMessage(ex1.getMessage());
            result.setCode("000000");
            result.setData(false);
            return result;
        }

        return result.errorResult(ex.getMessage());

    }

}
