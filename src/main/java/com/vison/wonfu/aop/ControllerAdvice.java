package com.vison.wonfu.aop;

import com.vison.wonfu.Response;
import com.vison.wonfu.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    private Response handleConstraintViolationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new Response(ResponseCode.parametrErrror, "参数错误", errors);
    }

    @ExceptionHandler(Exception.class)
    public Response handBaseException(Exception ex, HttpServletResponse response) throws Exception {
        if (ex instanceof MethodArgumentNotValidException) {
            return handleConstraintViolationException((MethodArgumentNotValidException) ex);
        }
        String errorMessage = "";
        if(ex.getMessage() == null){
            errorMessage = ex.getClass().toString();
        }else{
            errorMessage = ex.getMessage();
        }
        log.error(errorMessage);
        response.reset();
        response.setStatus(500);
        Map<String, StackTraceElement[]> errors = new HashMap<>();
        errors.put("errors", ex.getStackTrace());
        return new Response(ResponseCode.systemError,errorMessage, errors);
    }
}
