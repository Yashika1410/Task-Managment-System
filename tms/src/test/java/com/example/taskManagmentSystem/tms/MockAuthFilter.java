package com.example.taskManagmentSystem.tms;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class MockAuthFilter extends HttpFilter {

@Override
protected final void doFilter(final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
            ) throws IOException, ServletException {
                filterChain.doFilter(request, response);
            }
    
}
