package cn.miracle.framework.common.aop;

import cn.miracle.framework.common.exception.ExceptionBuilder;
import cn.miracle.framework.common.form.token.FormToken;
import cn.miracle.framework.common.model.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/11 22:41
 */
@Aspect
@Component
public class FormTokenAspect {

    public final static Logger LOGGER = LoggerFactory.getLogger(FormTokenAspect.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("@annotation(formToken)")
    public void formTokenValidate(FormToken formToken) {}

    @Before("formTokenValidate(formToken)")
    public void doBefore(JoinPoint joinPoint, FormToken formToken) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String formTokenToValidate = request.getHeader("formToken");
        if (StringUtils.isBlank(formTokenToValidate)) {
            ExceptionBuilder.build(CommonCode.MISSING_FORM_TOKEN);
        }
        boolean delete = stringRedisTemplate.delete(formTokenToValidate);
        if (!delete) {
            ExceptionBuilder.build(CommonCode.NOT_EXIST_FORM_TOKEN);
        }
        LOGGER.info(" {} -> {}  表单码验证成功！", LocalDateTime.now(), request.getRequestURI());
    }

}
