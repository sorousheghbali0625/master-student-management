
package org.soroush.masterstudentmanagement.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger log =
            LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    private static final String START_TIME = "requestStartTime";

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        request.setAttribute(
                START_TIME,
                System.nanoTime()
        );

        log.info(
                "Incoming request: {} {}",
                request.getMethod(),
                request.getRequestURI()
        );

        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            org.springframework.web.servlet.ModelAndView modelAndView) {

        log.debug(
                "Handler execution completed: {} {}",
                request.getMethod(),
                request.getRequestURI()
        );
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {

        Long startTime =
                (Long) request.getAttribute(START_TIME);

        if (startTime == null) {
            return;
        }

        long durationMs =
                (System.nanoTime() - startTime) / 1_000_000;

        log.info(
                "Completed: {} {} | Status: {} | Duration: {} ms",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                durationMs
        );

        if (ex != null) {
            log.error(
                    "Request failed: {} {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    ex
            );
        }
    }
}
