package com.example.taskManagmentSystem.tms.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthFilter extends HttpFilter {
    /**
     * list of uri which can bypass auth.
     */
    private List<String> listOfUris = new ArrayList<String>(
            Arrays.asList("tms/api/v1/api-docs/**",
                    "/api-docs/**",
                    "tms/api/v1/swagger-ui/**",
                    "/swagger-ui/**",
                    "tms/api/v1/swagger/**",
                    "/swagger/**"));
    /**
     * object of rest template to send request to auth service for auth.
     */
    private RestTemplate restTemplate = new RestTemplate();
    /**
     * contains auth service uri.
     */
    private String authUri;

    /**
     * Constructor which set auth uri value from application properties.
     * @param uri
     */
    public AuthFilter(@Value("${auth.url}") final String uri) {
        this.authUri = uri;
    }

    /**
     * logger class object to log.
     */
    private final Logger log = LoggerFactory.getLogger(
            AuthFilter.class);

    /**
     * Override method to authourize request.
     */

    @Override
    protected final void doFilter(final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
            ) throws IOException, ServletException {
        Stream<String> path = listOfUris.stream().filter(
                v -> new AntPathMatcher().match(v, request.getRequestURI()));
        if (path.count() > 0) {
            filterChain.doFilter(request, response);
        } else if (request.getHeader("Authorization") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", request.getHeader("Authorization"));
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            try {
                ResponseEntity<?> result = restTemplate.exchange(
                        authUri, HttpMethod.GET, entity, Map.class);
                if (result.getStatusCode() == HttpStatus.OK) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = (
                        Map<String, Object>) result.getBody();
                    request.setAttribute("userId", map.get("id"));
                    filterChain.doFilter(request, response);
                }
            } catch (HttpClientErrorException hte) {
                log.error(hte.getStatusText());
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                if (hte.getRawStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }
                PrintWriter writer = response.getWriter();
                writer.write(hte.getResponseBodyAsString());
                response.getWriter().flush();

            }
        }
    }
}
