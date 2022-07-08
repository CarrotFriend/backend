package com.carrotfriend.config;

import io.lettuce.core.RedisURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        RedisURI redisUri = RedisURI.create(System.getenv("REDIS_URL"));
        logger.info("url : {}", System.getenv("REDIS_URL"));
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        logger.info("host : {}",redisUri.getHost());
        logger.info("port : {}",redisUri.getPort());
        config.setPort(redisUri.getPort());
        config.setHostName(redisUri.getHost());
        config.setPassword(redisUri.getPassword());

        RedisConnectionFactory redisConnectionFactory = new LettuceConnectionFactory(config);
        return redisConnectionFactory;
    }


//    @Value("${spring.redis.host}")
//    private String redisHost;
//    @Value("${spring.redis.port}")
//    private int redisPort;
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(){
//        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisHost, redisPort);
//        return lettuceConnectionFactory;
//    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());

//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new GenericToStringSerializer<>(Integer.class));
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}