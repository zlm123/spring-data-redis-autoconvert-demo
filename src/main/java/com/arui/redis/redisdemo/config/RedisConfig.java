package com.arui.redis.redisdemo.config;

import com.arui.redis.redisdemo.dto.RedisPerson;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.database}")
    private int database;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        config.setPassword(password);
        config.setDatabase(database);
        return new JedisConnectionFactory(config);
    }

    @Bean
    RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {

        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper() {{
            this.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                    ObjectMapper.DefaultTyping.NON_FINAL,
                    JsonTypeInfo.As.WRAPPER_ARRAY);
        }};

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setEnableDefaultSerializer(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

//    @Bean
//    public RedisTemplate<String, RedisPerson> stringSerializerRedisTemplate3(RedisConnectionFactory factory) {
//        RedisTemplate<String, RedisPerson> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(factory);
//        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
//        Jackson2JsonRedisSerializer<RedisPerson> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(RedisPerson.class);
//        redisTemplate.setKeySerializer(stringSerializer);
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//
//        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setEnableDefaultSerializer(true);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//    @Bean
//    public RedisTemplate<String, Object> stringSerializerRedisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(factory);
//        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//
//        ObjectMapper objectMapper = new ObjectMapper();
////      objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
//                ObjectMapper.DefaultTyping.NON_FINAL,
//                JsonTypeInfo.As.WRAPPER_ARRAY);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//        redisTemplate.setKeySerializer(stringSerializer);
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//
//        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setEnableDefaultSerializer(true);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }

    /**
     * 对hash类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public RedisPerson getPerson() {
        return new RedisPerson("arui", 3);
    }
}
