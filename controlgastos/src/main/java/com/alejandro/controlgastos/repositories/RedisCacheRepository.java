package com.alejandro.controlgastos.repositories;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;


public class RedisCacheRepository {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheRepository.class);


    @CircuitBreaker(name = "redisRemoteCB", fallbackMethod = "fallback")
    public <T> T get(String key, TypeReference<T> typeRef) {

        try {
            String json = stringRedisTemplate.opsForValue().get(key);

            if (json == null) {
                logger.debug("Redis MISS key={}", key);
                return null;
            }

            logger.debug("Redis HIT key={}", key);
            return objectMapper.readValue(json, typeRef);

        } catch (JsonProcessingException e) {
            
            throw new RuntimeException(
                "Failed to deserialize value for key: " + key,
                e
            );
        }
    } 

    private <T> T fallback(String key, TypeReference<T> typeRef, Throwable ex) {

        logger.warn(
            "Key '{}' was not found in Redis for type '{}'. The reason is: {}",
            key,
            typeRef.getType(),
            ex.getMessage()
        );

        return null;
    }

    @CircuitBreaker(name = "redisRemoteCB", fallbackMethod = "fallbackSet")
    public void set(String key, Object value, long ttl) {

        try {
            String json = objectMapper.writeValueAsString(value);

            stringRedisTemplate
                    .opsForValue()
                    .set(key, json, ttl, TimeUnit.MINUTES);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    "Failed to serialize value for key: " + key,
                    e
            );
        }
    }

    private void fallbackSet(String key, Object value, long ttl, Throwable ex) {

        logger.warn(
            "Key '{}' was not saved in redis. The reason is {}",
            key,
            ex.getMessage()
        );
    }
    
}
