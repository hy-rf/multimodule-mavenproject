package com.backend.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@Slf4j
@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest) {
            String ipAddress = httpRequest.getHeader("X-Forwarded-For");
            if (ipAddress != null && ipAddress.contains(",")) {
                ipAddress = ipAddress.split(",")[0].trim();
            }
            if (ipAddress == null || ipAddress.isEmpty()) {
                ipAddress = request.getRemoteAddr();
            }
            log.info("Request from IP: {} URI: {}", ipAddress, httpRequest.getRequestURI());
        }
        chain.doFilter(request, response);
    }
}
