package cn.miracle.framework.common.util;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/4 0:20
 */
public class IdWorker {

    /**
     *  时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
     */
    private final static long twepoch = 1288834974657L;

    /**
     *  机器标识位数
     */
    private final static long workerIdBits = 5L;

    /**
     * 数据中心标识位数
     */
    private final static long dataCenterIdBits = 5L;

    /**
     * 毫秒内自增位
     */
    private final static long sequenceBits = 12L;

    /**
     * 机器ID最大值
     */
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 数据中心ID最大值
     */
    private final static long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);

    /**
     * 机器ID偏左移12位
     */
    private final static long workerIdShift = sequenceBits;

    /**
     * 数据中心ID左移17位
     */
    private final static long dataCenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间毫秒左移22位
     */
    private final static long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 上次生产id时间戳
     */
    private static long lastTimestamp = -1L;

    /**
     * 并发控制
     */
    private long sequence = 0L;

    /**
     * 机器标识id部分
     */
    private final long workerId;

    /**
     * 数据标识id部分
     */
    private final long dataCenterId;

    public IdWorker(){
        this.dataCenterId = getDataCenterId(maxDataCenterId);
        this.workerId = getMaxWorkerId(dataCenterId, maxWorkerId);
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tillNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        long nextId = ((timestamp - twepoch) << timestampLeftShift)
                | (dataCenterId << dataCenterIdShift)
                | (workerId << workerIdShift) | sequence;

        return nextId;
    }

    public IdWorker(long workerId, long dataCenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker-Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("data-center-Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    protected static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
        StringBuffer mpid = new StringBuffer();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            /*
             * GET jvmPid
             */
            mpid.append(name.split("@")[0]);
        }
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    protected static long getDataCenterId(long maxDataCenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (maxDataCenterId + 1);
            }
        } catch (Exception e) {
            System.out.println(" getDataCenterId: " + e.getMessage());
        }
        return id;
    }

    private long tillNextMillis(final long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }


}
