package com.lyf.manager;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class RedisManager {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public Object getValue(String key){
        //string
        //list
        //hash
        //set
        //zset
        return redisTemplate.opsForList().range(key,0,-1);
    }

    public <T> Object setValue(String key,  Collection<T> data) {
        //string
        //list
        //hash
        //set
        //zset
        Object[] t  = new Object[data.size()];
        data.toArray(t);//变成数组放进去,可以将数据分开存储,否则数据会统一放进去
        return redisTemplate.opsForList().leftPushAll(key, t);
    }
}
