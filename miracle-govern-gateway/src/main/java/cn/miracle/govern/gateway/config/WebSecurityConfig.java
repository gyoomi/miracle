package cn.miracle.govern.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/4 19:03
 */
@Configuration
@EnableWebSecurity
@Order(111)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login", "/auth/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();

    }
}
