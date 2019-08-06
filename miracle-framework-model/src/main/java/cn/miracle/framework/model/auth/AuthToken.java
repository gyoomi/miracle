package cn.miracle.framework.model.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/6 21:22
 */
@Data
@NoArgsConstructor
@ToString
public class AuthToken {

    /**
     * jti
     */
    private String jti;

    /**
     * for refresh
     */
    private String refreshToken;

    /**
     * token
     */
    private String jwtToken;

}
