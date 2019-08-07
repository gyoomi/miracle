/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package cn.miracle.service.manage.auth.feign;

import cn.miracle.framework.common.constant.ServiceApplicationList;
import cn.miracle.service.manage.auth.feign.fallback.TestFeignClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * 接口功能描述
 *
 * @author Leon
 * @version 2019/8/7 9:33
 */
@FeignClient(value = ServiceApplicationList.MIRACLE_SERVICE_MANAGE_USER, fallback = TestFeignClientFallBack.class)
public interface TestFeignClient {

    @GetMapping(value = "/manage/user/test/test1")
    Map test1();

    @GetMapping(value = "/manage/user/test/test2")
    Map test2();
}
