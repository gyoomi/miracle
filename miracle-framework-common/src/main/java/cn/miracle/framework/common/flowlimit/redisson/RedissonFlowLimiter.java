package cn.miracle.framework.common.flowlimit.redisson;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * The annotation of flow limiter for redisson
 *
 * @author Leon
 * @version 2019/8/19 23:18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RedissonFlowLimiter {

    LimiterStrategy limiterStrategy();

    /**
     * This is for {@link org.springframework.web.bind.annotation.PathVariable } {@code path}
     *
     * <p>
     *     Like '/test/{id}', its value of path should be <em>/test</em>
     *     Like '/test/save'  default value that equals the value of requestURI
     * </p>
     *
     * @return
     */
    String path() default "";

    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * Number of requests allowed per unit time
     *
     * @return
     */
    int permits();

}
