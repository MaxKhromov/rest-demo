package ru.rest.demo.aspects;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @noinspection EmptyMethod, unused
 */
@Component
@Slf4j
@Aspect
public class RequestLogAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllers() {
    }

    @Pointcut("execution(public * *(..))")
    public void publicMethods() {
    }

    @Around("restControllers() && publicMethods()")
    public Object log(final ProceedingJoinPoint pjp) throws Throwable {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();
        final MethodSignature signature = (MethodSignature) pjp.getSignature();
        final StopWatch stopWatch = new StopWatch();

        try {
            final String arguments = IntStream.iterate(0, i -> i + 1)
                    .limit(Math.min(signature.getParameterNames().length, pjp.getArgs().length))
                    .mapToObj(i -> signature.getParameterNames()[i] + "=" + pjp.getArgs()[i])
                    .collect(Collectors.joining(","));

            log.info(
                    "Started execution of {} {} {} with arguments {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getRemoteAddr(),
                    arguments);

            stopWatch.start();
            final Object value = pjp.proceed();
            stopWatch.stop();

            log.info("Finished execution of {} {} {} (running {} ns)",
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getRemoteAddr(),
                    stopWatch.getTotalTimeNanos());

            return value;

        } catch (Exception exception) {
            log.warn("Failed execution of {} {} {} (running {} ns)",
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getRemoteAddr(),
                    stopWatch.getTotalTimeNanos());
            log.warn(exception.getMessage());
            throw exception;
        }
    }
}
