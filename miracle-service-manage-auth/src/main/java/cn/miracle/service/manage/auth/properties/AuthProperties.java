package cn.miracle.service.manage.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/6 21:27
 */
@Component
@ConfigurationProperties("auth")
public class AuthProperties {

    private int tokenValiditySeconds;

    private String clientId;

    private String clientSecret;

    public AuthProperties() {}

    public int getTokenValiditySeconds() {
        return tokenValiditySeconds;
    }

    public void setTokenValiditySeconds(int tokenValiditySeconds) {
        this.tokenValiditySeconds = tokenValiditySeconds;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

}
