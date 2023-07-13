package com.example.taskManagmentSystem.tms.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.taskManagmentSystem.tms.TmsApplication;

/**
 * Filter class used to log each request.
 */
@Component
public class LoggerMiddleware extends OncePerRequestFilter {
    /**
     * log variable which used for logging.
     */
    private final Logger log = LoggerFactory.getLogger(
            TmsApplication.class);

    /**
     * Override method to log request parameters and
     * response parameter once per request.
     */

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain)
            throws ServletException, IOException {

        filterChain.doFilter(request, response);
        log.info(request.getRemoteAddr() + " - " + request.getMethod()
                + " " + request.getRequestURI() + " " + response.getStatus() + " "
                + HttpStatus.valueOf(response.getStatus()).getReasonPhrase());

    }

}
