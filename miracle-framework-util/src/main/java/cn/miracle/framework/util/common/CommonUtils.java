package cn.miracle.framework.util.common;

import java.util.UUID;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/3 18:04
 */
public class CommonUtils {

    /**
     * 获取UUID(不带中横线)
     *
     * @return
     */
    public static String getUUID(){
        String s = UUID.randomUUID().toString();
        return s.replace("-","");
    }
}
