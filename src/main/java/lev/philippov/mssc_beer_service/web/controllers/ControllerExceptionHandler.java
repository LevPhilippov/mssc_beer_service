package lev.philippov.mssc_beer_service.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity handleBindingException(ConstraintViolationException e) {
            List<String> errors = new ArrayList<>(e.getConstraintViolations().size());
            e.getConstraintViolations().forEach(ex-> errors.add(ex.toString()));
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }

}
