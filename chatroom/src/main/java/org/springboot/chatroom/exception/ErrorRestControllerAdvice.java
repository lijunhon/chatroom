package org.springboot.chatroom.exception;

import org.springboot.chatroom.dto.ReturnMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@RestControllerAdvice
public class ErrorRestControllerAdvice {
    /** 全局异常捕捉处理 返回 401 状态 */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ReturnMessageDto errorHandler(Exception ex) {
        return new ReturnMessageDto(401,"ERROR", ex.getMessage());
    }

    /** 自定义异常捕获,返回 500 状态 */
    @ExceptionHandler(value = MyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ReturnMessageDto myException(MyException e) {
        return new ReturnMessageDto(500, e.getStatus(), e.getMessage());
    }

    /** Http Method 异常 返回 405 */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ReturnMessageDto httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return new ReturnMessageDto(405, "ERROR", e.getMessage());
    }

    /** 404异常,返回 404 NOT_FOUND 异常 */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ReturnMessageDto noHandlerFoundException(NoHandlerFoundException e) {
        return new ReturnMessageDto(404, "ERROR", "资源不存在");
    }

    /** RequestBody 为空时返回此错误提醒,返回400 bad Request */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ReturnMessageDto httpMessageNotReadableException() {
        return new ReturnMessageDto(400,"ERROR", "请传入参数");
    }

    /** RequestBody某个必须输入的参数为空时 返回 400 Bad Request */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ReturnMessageDto methodDtoNotValidException(Exception ex) {
        MethodArgumentNotValidException c = (MethodArgumentNotValidException) ex;
        List<ObjectError> errors = c.getBindingResult().getAllErrors();
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(x -> errorMsg.append(x.getDefaultMessage()).append(" "));
        return new ReturnMessageDto(400,"ERROR", errorMsg.toString());
    }
}
