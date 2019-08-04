package cn.miracle.framework.common.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/4 1:00
 */
@ConfigurationProperties(prefix = "miracle.work")
public class IdWorkProperties {

    /**
     * 当前机器id
     */
    private long workerId;

    /**
     * 序列号
     */
    private long dataCenterId;

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public long getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }
}
