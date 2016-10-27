package pl.dors.radek.followme.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HTTP_STATUS.value());
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HTTP_STATUS);
    }

}