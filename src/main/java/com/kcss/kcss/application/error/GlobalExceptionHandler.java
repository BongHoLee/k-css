package com.kcss.kcss.application.error;

import com.kcss.kcss.application.dto.BaseResponse;
import com.kcss.kcss.global.error.BusinessException;
import com.kcss.kcss.global.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * javax.validation.Valid or @Validated 으로 binding error 시 발생 HttpMessageConverter 에서 등록한 HttpMessageConverter
     * binding 못할경우 발생 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        final BaseResponse response = BaseResponse.errorOf(ApplicationErrorCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<BaseResponse> requestParameterMissingException(MissingServletRequestParameterException e) {
        log.error("requestParameterMissingException", e);
        final BaseResponse response = BaseResponse.errorOf(ApplicationErrorCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<BaseResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("NOT SUPPORTED HTTP METHOD CALLED", e);
        final BaseResponse response = BaseResponse.errorOf(ApplicationErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 도메인/비즈니스 규칙과 관련된 예외 발생
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<BaseResponse> handleBusinessException(final BusinessException e) {
        log.error("Business Exception", e);
        final ErrorCode errorCode = e.getErrorCode();
        final BaseResponse response = BaseResponse.errorOf(errorCode);
        if (e.getErrorCode() instanceof ApplicationErrorCode) {
            return new ResponseEntity<>(response, HttpStatus.valueOf(e.getErrorCode().getStatus()));
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * DB Exception
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<BaseResponse> handleDataViolationException(final DataIntegrityViolationException e) {
        log.error("Data Violation Exception", e);
        final BaseResponse response = new BaseResponse(false, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseResponse> handleException(Exception e) {
        log.error("INTERNAL SERVER ERROR", e);
        final BaseResponse response = BaseResponse.errorOf(ApplicationErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
