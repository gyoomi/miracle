package cn.miracle.framework.common.flowlimit.redisson;

import cn.miracle.framework.common.exception.ExceptionBuilder;
import cn.miracle.framework.common.model.response.CommonCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
public class RedissonFlowLimiterAspect {

    private static final String[] IP_HEADER_KYES = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP"};

    private Map<String, RRateLimiter> rateMap = new ConcurrentHashMap<>();

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(redissonFlowLimiter)")
    public void pointCut(RedissonFlowLimiter redissonFlowLimiter) {}

    @Around(value = "pointCut(redissonFlowLimiter)", argNames = "pjp,redissonFlowLimiter")
    public Object flowLimiter(ProceedingJoinPoint pjp, RedissonFlowLimiter redissonFlowLimiter) throws Throwable {
        String path = redissonFlowLimiter.path();
        int permits = redissonFlowLimiter.permits();
        LimiterStrategy limiterStrategy = redissonFlowLimiter.limiterStrategy();
        TimeUnit timeUnit = redissonFlowLimiter.timeUnit();
        String limitKey;
        String requestUri = getRequestUri();
        if (path == null || path.equals("")) {
            path = requestUri;
        }
        limitKey = path;
        if (limiterStrategy == LimiterStrategy.IP) {
            limitKey += getIP();
        }

        RRateLimiter rateLimiter;
        if (rateMap.containsKey(limitKey)) {
            rateLimiter = rateMap.get(limitKey);
        } else {
            RateIntervalUnit rateIntervalUnit;
            if (timeUnit == TimeUnit.SECONDS) {
                rateIntervalUnit = RateIntervalUnit.SECONDS;
            } else if (timeUnit == TimeUnit.MINUTES) {
                rateIntervalUnit = RateIntervalUnit.MINUTES;
            } else if (timeUnit == TimeUnit.HOURS) {
                rateIntervalUnit = RateIntervalUnit.HOURS;
            } else if (timeUnit == TimeUnit.DAYS) {
                rateIntervalUnit = RateIntervalUnit.DAYS;
            } else {
                // default time unit
                rateIntervalUnit = RateIntervalUnit.SECONDS;
            }


            rateLimiter = redissonClient.getRateLimiter(limitKey);
            boolean setRate = rateLimiter.trySetRate(RateType.OVERALL, permits, 1, rateIntervalUnit);
            if (!setRate) {
                ExceptionBuilder.build(CommonCode.FAIL);
            }
            rateMap.put(limitKey, rateLimiter);
        }

        boolean success = rateLimiter.tryAcquire();
        if (!success) {
            // fallback
            ExceptionBuilder.build(CommonCode.REQUEST_ERROR_FREQUENT);
        }
        return pjp.proceed();
    }

    private String getRequestUri() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest().getRequestURI();
    }

    private String getIP() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        for (String ipHeaderKey : IP_HEADER_KYES) {
            String ip = request.getHeader(ipHeaderKey);
            if (ip != null && ip.length() != 0 && (!"unknown".equalsIgnoreCase(ip))) {
                return ip;
            } else {
                return request.getRemoteHost();
            }
        }
        return null;
    }
}
