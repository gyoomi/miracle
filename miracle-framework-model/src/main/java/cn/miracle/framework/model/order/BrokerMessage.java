/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package cn.miracle.framework.model.order;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * brokerMessage
 *
 * @author Leon
 * @version 2019/8/19 9:58
 */
@Data
@Entity
@Table(name = "brokerMessage")
public class BrokerMessage implements Serializable {

    @Id
    private Long id;

    /**
     * business type
     */
    private String messageQueueType;

    /**
     * exchangeName
     */
    private String exchangeName;

    /**
     * routingKey
     */
    private String routingKey;

    /**
     * the content of message
     */
    private String content;

    /**
     * delayed:
     *    1 - yes
     *    0 - no
     */
    private Integer isDelayed;

    /**
     * the time of delay, the unit of time is <b>ms</b>
     */
    private Integer delayTime;

    /**
     * the headers
     */
    private String headers;

    /**
     * the count of retry
     */
    private Integer retryCount = 0;

    /**
     * version
     */
    private Integer version = 0;

    /**
     * status
     */
    private Integer status;

    /**
     * the cause of failure
     */
    private String errorMsg;

    /**
     * createDate
     */
    private Date createDate;

    /**
     * updateDate
     */
    private Date updateDate;

    /**
     * nextRetryDate
     */
    private Date nextRetryDate;
}
