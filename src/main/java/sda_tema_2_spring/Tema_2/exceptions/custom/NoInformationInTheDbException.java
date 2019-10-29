package sda_tema_2_spring.Tema_2.exceptions.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoInformationInTheDbException extends RuntimeException {

    public NoInformationInTheDbException() {
    }

    public NoInformationInTheDbException(String message) {
        super(message);
    }

    public NoInformationInTheDbException(String message, Throwable cause) {
        super(message, cause);
    }
}

