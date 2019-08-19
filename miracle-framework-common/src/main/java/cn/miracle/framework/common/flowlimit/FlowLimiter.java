package cn.miracle.framework.common.flowlimit;

import java.lang.annotation.*;

/**
 * The annotation of flow limiter
 *
 * @author Leon
 * @version 2019/8/19 23:18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface FlowLimiter {

    /**
     * Number of tokens placed in the bucket per second
     *
     * @return
     */
    double permitsPerSecond();

    /**
     * Service downgrade after time is exceeded
     *
     * @return
     */
    long timeout();
}
