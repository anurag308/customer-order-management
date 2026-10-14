package com.example.customerorders.logging;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.regex.Pattern;

@Aspect
@Component
public class RequestResponseLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingAspect.class);
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "(?i)\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}\\b");

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = currentRequest();
        String method = request == null ? "UNKNOWN" : request.getMethod();
        String uri = request == null ? "UNKNOWN" : request.getRequestURI();
        logger.info("HTTP request {} {} controller={} arguments={}", method, uri,
            joinPoint.getSignature().toShortString(), safeLogValue(Arrays.deepToString(joinPoint.getArgs())));

        long startedAt = System.nanoTime();
        try {
            Object response = joinPoint.proceed();
            logger.info("HTTP response {} {} status=success durationMs={} body={}", method, uri,
                    (System.nanoTime() - startedAt) / 1_000_000, safeLogValue(response));
            return response;
        } catch (Throwable exception) {
            logger.warn("HTTP response {} {} status=error durationMs={} exception={}", method, uri,
                    (System.nanoTime() - startedAt) / 1_000_000, exception.toString());
            throw exception;
        }
    }

    private HttpServletRequest currentRequest() {
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes) {
            return attributes.getRequest();
        }
        return null;
    }

    private String safeLogValue(Object value) {
        return EMAIL_PATTERN.matcher(String.valueOf(value)).replaceAll("[REDACTED_EMAIL]");
    }
}