package cn.miracle.framework.common.form.token;

import java.lang.annotation.*;

/**
 * This is used to prevent duplicate submissions from forms
 *
 * @author Leon
 * @version 2019/8/11 22:26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormToken {

    /**
     * It is valid by default
     *
     * @return value
     */
    boolean value() default true;

}
