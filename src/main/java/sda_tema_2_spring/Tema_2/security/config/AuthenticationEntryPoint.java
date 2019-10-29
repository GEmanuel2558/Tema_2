package sda_tema_2_spring.Tema_2.security.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.name());
    }
}
