package cn.miracle.framework.common.flowlimit;

import cn.miracle.framework.common.exception.ExceptionBuilder;
import cn.miracle.framework.common.model.response.CommonCode;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * FlowLimiterAspect
 *
 * @author Leon
 * @version 2019/8/19 23:23
 */
@Aspect
@Component
@Slf4j
public class FlowLimiterAspect {

    private Map<String, RateLimiter> rateMap = new ConcurrentHashMap<>();

    @Pointcut("@annotation(flowLimiter)")
    public void pointCut(FlowLimiter flowLimiter) {}

    @Around(value = "pointCut(flowLimiter)", argNames = "pjp,flowLimiter")
    public Object flowLimiter(ProceedingJoinPoint pjp, FlowLimiter flowLimiter) throws Throwable {
        String requestUri = getRequestUri();
        RateLimiter rateLimiter;
        if (rateMap.containsKey(requestUri)) {
            rateLimiter = rateMap.get(requestUri);
        } else {
            double permitsPerSecond = flowLimiter.permitsPerSecond();
            rateLimiter = RateLimiter.create(permitsPerSecond);
            rateMap.put(requestUri, rateLimiter);
        }
        long timeOut = flowLimiter.timeout();
        // 获取令牌桶中的令牌，如果自规定时间内，没有获取到令牌，则服务降级
        boolean tryAcquire = rateLimiter.tryAcquire(timeOut, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            // fallback();
            ExceptionBuilder.build(CommonCode.REQUEST_ERROR_FREQUENT);
            return null;
        }
        return pjp.proceed();
    }

    /**
     * 服务降级
     */
    private void fallback() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        String requestURI = attributes.getRequest().getRequestURI();
        response.setHeader("Content-Type", "text/html;charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            log.warn("FlowLimiter url : {} 请求频繁！ ", requestURI);
            writer.println("服务繁忙！请稍后重试！");
        } catch (IOException e) {
            log.error("FlowLimiter io error:{}", e.getMessage());
        }
    }

    private String getRequestUri() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest().getRequestURI();
    }
}
