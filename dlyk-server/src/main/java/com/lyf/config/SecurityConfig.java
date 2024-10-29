package com.lyf.config;

import com.lyf.config.filter.TokenVerifyFilter;
import com.lyf.config.handler.MyAuthenticationFailureHandler;
import com.lyf.config.handler.MyAuthenticationSuccessHandler;
import com.lyf.config.handler.MyLogoutSuccessHandler;
import com.lyf.constant.Constants;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@EnableWebSecurity
@Configuration
public class SecurityConfig {


    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Resource
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Resource
    private TokenVerifyFilter tokenVerifyFilter;

    @Bean //There is no PasswordEncoder mapped for the id "null"
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,CorsConfigurationSource configurationSource) throws Exception {
        return httpSecurity
                .formLogin((formLogin) -> {
                    formLogin.loginProcessingUrl(Constants.LOGIN_URI)//设置登录的处理地址 不需要写controller
                            .usernameParameter("loginAct")//用户名参数设置
                            .passwordParameter("loginPwd")//设置密码参数
                            .successHandler(myAuthenticationSuccessHandler)
                            .failureHandler(myAuthenticationFailureHandler);
                })
                //filter改变了默认的行为,其中的请求就不拦截了,需要对请求进行拦截
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/api/login").permitAll()
                            .anyRequest().authenticated();//任何请求都需要登录后才能访问
                })
                .csrf(AbstractHttpConfigurer::disable)//方法引用 禁用跨站请求伪造

                //支持跨域请求 需要配置一个bean
                .cors((cors) -> {
                    cors.configurationSource(configurationSource);
                })
                .sessionManagement((session) -> {
                    //session创建策略
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 无session状态，也就是禁用session
                })

                //添加自定义的Filter
                .addFilterBefore(tokenVerifyFilter, LogoutFilter.class)

                //退出登录
                .logout((logout) -> {
                    logout.logoutUrl("/api/logout") //退出提交到该地址，该地址不需要我们写controller的，是框架处理
                            .logoutSuccessHandler(myLogoutSuccessHandler);
                })

                .build();
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); //允许任何来源，http://localhost:8081
        configuration.setAllowedMethods(Arrays.asList("*")); //允许任何请求方法，post、get、put、delete
        configuration.setAllowedHeaders(Arrays.asList("*")); //允许任何的请求头

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
