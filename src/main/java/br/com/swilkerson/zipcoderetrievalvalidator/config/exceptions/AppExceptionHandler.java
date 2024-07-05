package br.com.swilkerson.zipcoderetrievalvalidator.config.exceptions;

import br.com.swilkerson.zipcoderetrievalvalidator.config.exceptions.model.DefaultException;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.exceptions.InvalidZipcodeException;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.exceptions.ZipcodeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionHandler {
    private static final String BAD_REQUEST = "Bad Request";
    private static final String NOT_FOUND = "Not Found";

    @ExceptionHandler(InvalidZipcodeException.class)
    public ResponseEntity<DefaultException> handlerInvalidZipcode(InvalidZipcodeException ex) {
        return new ResponseEntity<>(
                new DefaultException(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), BAD_REQUEST,
                        ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ZipcodeNotFoundException.class)
    public ResponseEntity<DefaultException> handlerZipcodeNotFound(ZipcodeNotFoundException ex) {
        return new ResponseEntity<>(
                new DefaultException(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), NOT_FOUND,
                        ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
