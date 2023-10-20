package is442.portfolioAnalyzer.filter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AccessLogFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().toLowerCase().contains("/")) {
            long time = System.currentTimeMillis();
            try {
                filterChain.doFilter(request, response);
            }
            finally {
                time = System.currentTimeMillis() - time;

                String remoteIpAddress = request.getHeader("X-FORWARDED-FOR");

                if (remoteIpAddress == null || remoteIpAddress.isEmpty()) {
                    remoteIpAddress = request.getRemoteAddr();
                }

                log.info("{} {} {} {} {} {}ms", remoteIpAddress, request.getMethod(), request.getRequestURI(), response.getContentType(),
                        response.getStatus(), time);

            }
        }
        else {
            filterChain.doFilter(request, response);
        }

    }
}
