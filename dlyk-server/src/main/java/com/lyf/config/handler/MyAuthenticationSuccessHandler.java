package com.lyf.config.handler;


import com.lyf.constant.Constants;
import com.lyf.model.TUser;
import com.lyf.result.R;
import com.lyf.service.RedisService;
import com.lyf.util.JSONUtils;
import com.lyf.util.JWTUtils;
import com.lyf.util.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication/*登录后的用户信息*/) throws IOException, ServletException {
        //登录成功，执行该方法，在该方法中返回json给前端，就行了
        TUser tUser = (TUser) authentication.getPrincipal();

        //1、生成jwt
        //把tUser对象转成json作为负载数据放入jwt
        String userJSON = JSONUtils.toJSON(tUser);
        String jwt = JWTUtils.createJWT(userJSON);

        //2、写入redis
        redisService.setValue(Constants.REDIS_JWT_KEY + tUser.getId(), jwt);

        //3、设置jwt的过期时间(如果选择了记住我，过期时间是7天，否则是30分钟)
        String rememberMe = request.getParameter("rememberMe");
        if (Boolean.parseBoolean(rememberMe)) {//字符串转为boolean
            redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(), Constants.EXPIRE_TIME, TimeUnit.SECONDS);
        } else {
            redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(), Constants.DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
        }

        R result = R.OK(jwt);

        //把R对象转成json
        String resultJSON = JSONUtils.toJSON(result);

        //把R以json返回给前端
        ResponseUtils.write(response, resultJSON);
    }
}
