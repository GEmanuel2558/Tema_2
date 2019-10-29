package sda_tema_2_spring.Tema_2.exceptions.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConstraintViolationException extends RuntimeException {

    public ConstraintViolationException() {
    }

    public ConstraintViolationException(String message) {
        super(message);
    }

    public ConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConstraintViolationException(Throwable cause) {
        super(cause);
    }
}
