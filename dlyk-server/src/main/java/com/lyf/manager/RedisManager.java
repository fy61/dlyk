package com.lyf.manager;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public String getOnlyNumber(String key) {

        //希望体现出交易的时间
        LocalDateTime localDateTime = LocalDateTime.now(); //java.time.* 包下的类，拿到当前的日期和时间（年月日时分秒）
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"); //日期时间的格式
        String formattedDateTime = localDateTime.format(formatter);

        //从redis中拿到一个自增值，这个自增值每次都是不重复的，为什么redis自增的值不重复呢？原因是因为redis的命令的执行是单线程的；
        Long incrValue = redisTemplate.opsForValue().increment(key);
        return formattedDateTime + incrValue;
    }
}
