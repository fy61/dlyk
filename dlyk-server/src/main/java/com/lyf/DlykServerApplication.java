package com.lyf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;


@EnableWebSecurity//解决无法自动装配问题需要的注解  包含了configuration注解
@MapperScan(basePackages = {"com.lyf.mapper"})
@SpringBootApplication//表示此类为主启动类
public class DlykServerApplication implements CommandLineRunner {

    public static final Map<String, Object> cacheMap = new HashMap<>();

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    public static void main(String[] args) {
        SpringApplication.run(DlykServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //springboot项目启动后，把redisTemplate这个Bean修改一下，修改一下key和value的序列化方式

        //设置key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //对象映射工具，Java对象 和 json对象进行相互转化
        ObjectMapper mapper = new ObjectMapper();
        //设置可见性 许访问所有的 Java 属性
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //激活类型 确保在进行 JSON 转换时，保存对象的类型信息
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.EVERYTHING);

        //设置value序列化
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(mapper, Object.class));

        //设置hashKey序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        //设置haskValue序列化
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(mapper, Object.class));
    }
}
