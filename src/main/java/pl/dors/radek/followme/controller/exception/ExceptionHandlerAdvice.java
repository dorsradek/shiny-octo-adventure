package pl.dors.radek.followme.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HTTP_STATUS.value());
        LOGGER.error(e.getMessage());
        error.setMessage("Error");
        return new ResponseEntity<>(error, HTTP_STATUS);
    }

}