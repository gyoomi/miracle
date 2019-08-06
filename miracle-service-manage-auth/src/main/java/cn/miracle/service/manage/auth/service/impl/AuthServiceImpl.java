package cn.miracle.service.manage.auth.service.impl;

import cn.miracle.framework.common.constant.ServiceApplicationList;
import cn.miracle.framework.common.exception.ExceptionBuilder;
import cn.miracle.framework.model.auth.AuthCode;
import cn.miracle.framework.model.auth.AuthToken;
import cn.miracle.service.manage.auth.properties.AuthProperties;
import cn.miracle.service.manage.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/6 21:21
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthProperties authProperties;

    /**
     * {@inheritDoc}
     *
     * @param loginName
     * @param password
     * @param clientId
     * @param clientSecret
     * @return
     */
    @Override
    public AuthToken login(String loginName, String password, String clientId, String clientSecret) {
        AuthToken token = applyToken(loginName, password, clientId, clientSecret);
        if (token == null) {
            ExceptionBuilder.build(AuthCode.AUTH_APPLY_TOKEN_ERROR);
        }
        String jwtToken = token.getJwtToken();
        boolean result = saveToken(jwtToken, "", authProperties.getTokenValiditySeconds());
        if (!result) {
            ExceptionBuilder.build(AuthCode.AUTH_SAVE_TOKEN_ERROR);
        }
        return token;
    }

    /**
     * 申请令牌
     *
     * @param username
     * @param password
     * @param clientId
     * @param clientSecret
     * @return
     */
    private AuthToken applyToken(String username, String password, String clientId, String clientSecret) {
        ServiceInstance serviceInstance = loadBalancerClient.choose(ServiceApplicationList.MIRACLE_SERVICE_MANAGE_AUTH);
        // 此地址就是http://ip:port
        URI uri = serviceInstance.getUri();
        // 令牌申请的地址 http://localhost:40400/auth/oauth/token
        String authUrl = uri + "/auth/oauth/token";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", getHttpBasic(clientId, clientSecret));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);

        // 设置restTemplate远程调用时候，对400和401不让报错，正确返回数据
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if(response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401){
                    super.handleError(response);
                }
            }
        });

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity, Map.class);

        // 申请令牌信息
        Map bodyMap = response.getBody();
        if(bodyMap == null ||
                bodyMap.get("access_token") == null ||
                bodyMap.get("refresh_token") == null ||
                bodyMap.get("jti") == null) {

            //解析spring security返回的错误信息
            if(bodyMap != null && bodyMap.get("error_description") != null) {
                String error_description = (String) bodyMap.get("error_description");
                // TODO
                if(error_description.indexOf("UserDetailsService returned null") >= 0){
                    ExceptionBuilder.build(AuthCode.AUTH_ACCOUNT_NOT_EXISTS);
                }else if (error_description.indexOf("坏的凭证") >=0 ){
                    ExceptionBuilder.build(AuthCode.AUTH_CREDENTIAL_ERROR);
                }
            }

            return null;
        }

        AuthToken authToken = new AuthToken();
        authToken.setJti(((String) bodyMap.get("jti")));
        authToken.setJwtToken(((String) bodyMap.get("access_token")));
        authToken.setRefreshToken(((String) bodyMap.get("refresh_token")));

        return authToken;
    }

    /**
     * 保存到redis
     *
     * @param accessToken
     * @param content
     * @param ttl
     * @return
     */
    private boolean saveToken(String accessToken, String content, long ttl) {
        String key = "user_token:" + accessToken;
        stringRedisTemplate.boundValueOps(key).set(content, ttl, TimeUnit.SECONDS);
        Long expire = stringRedisTemplate.getExpire(key);
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * Base64编码
     *
     * @param clientId
     * @param clientSecret
     * @return
     */
    private String getHttpBasic(String clientId, String clientSecret){
        String string = clientId + ":" + clientSecret;
        // 将串进行base64编码
        byte[] encode = Base64Utils.encode(string.getBytes());
        return "Basic " + new String(encode);
    }
}
