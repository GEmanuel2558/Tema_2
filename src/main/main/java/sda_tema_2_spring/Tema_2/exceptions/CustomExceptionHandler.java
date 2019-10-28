package sda_tema_2_spring.Tema_2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import sda_tema_2_spring.Tema_2.exceptions.custom.NoInformationInTheDb;
import sda_tema_2_spring.Tema_2.exceptions.model.ErrorResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(ServletRequestBindingException.class)
    public final ResponseEntity<ErrorResponse> handleHeaderException(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Bad Request", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Server Error", details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(NoInformationInTheDb.class)
    public final ResponseEntity<ErrorResponse> noInformationInTheDb() {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.name(), Arrays.asList("No information on the Db"));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }
}