package com.nareun.aircraft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.nareun.aircraft.domain.Aircraft;

@Configuration
public class RedisConfig {//~> Repository구현으로 인해 RedisConfig가 필요 없어짐.

    @Bean
    public RedisOperations<String, Aircraft> redidOperations(RedisConnectionFactory factory) {
        //* 객체와 JSON 레코드 간 변환 시 사용할 serializer 
        Jackson2JsonRedisSerializer<Aircraft> serializer = new Jackson2JsonRedisSerializer<>(Aircraft.class);
        
        RedisTemplate<String, Aircraft> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setDefaultSerializer(serializer);  
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }
}
