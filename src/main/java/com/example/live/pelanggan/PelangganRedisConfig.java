package com.example.live.pelanggan; 

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import java.net.UnknownHostException;
import java.io.Serializable;
import java.util.List;

@Configuration
public class PelangganRedisConfig implements Serializable{

    @Bean
    public LettuceConnectionFactory getConnectionFactory(){
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory("127.0.0.1", 6379);
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisPelangganTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(getConnectionFactory());
        return template;
    }
}