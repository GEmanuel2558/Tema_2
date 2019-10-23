package sda_tema_2_spring.Tema_2.filters;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Component
@Order(2)
public class RequestResponseLoggingFilter implements Filter {

    final static Logger logger = Logger.getLogger(RequestResponseLoggingFilter.class.getSimpleName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        logger.info("Request " + req.getMethod() + ": " + req.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
        logger.info("Response:  " + res.getContentType());
    }
}
