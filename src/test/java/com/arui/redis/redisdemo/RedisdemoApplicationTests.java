package com.arui.redis.redisdemo;

import com.arui.redis.redisdemo.Utils.RedisUntil;
import com.arui.redis.redisdemo.dto.Gender;
import com.arui.redis.redisdemo.dto.Person;
import com.arui.redis.redisdemo.dto.PersonRepository;
import com.arui.redis.redisdemo.dto.RedisPerson;
import com.arui.redis.redisdemo.service.RedisServices;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisdemoApplicationTests {

    @Autowired
    RedisServices redisServices;
    @Autowired
    RedisOperations<String, String> operations;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    PersonRepository repository;

    @Autowired
    RedisUntil redisUntil;


    @Test
    public void saveObjWithJSON1() {
        RedisPerson redisPerson = new RedisPerson();
        redisPerson.setName("arui");
        redisPerson.setAge(35);
        redisTemplate.opsForHash().put("aaa", "abc", redisPerson);
        RedisPerson redisPerson1 = redisUntil.getRedisHashObj("aaa", "abc");
        assertThat(redisPerson1);
    }

    @Test
    public void saveObjWithJSON2() {
        Person person = new Person();
        person.setFirstname("arui");
        person.setId("1");
        redisTemplate.opsForHash().put("aaa", "bbc", person);
        Person person1 = redisUntil.getRedisHashObj("aaa", "abc");
        assertThat(person1);
    }


    /**
     * {@link Charset} for String conversion
     **/
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    /*
     * Set of test users
     */
    Person eddard = new Person("eddard", "stark", Gender.MALE);
    Person robb = new Person("robb", "stark", Gender.MALE);
    Person sansa = new Person("sansa", "stark", Gender.FEMALE);
    Person arya = new Person("arya", "stark", Gender.FEMALE);
    Person bran = new Person("bran", "stark", Gender.MALE);
    Person rickon = new Person("rickon", "stark", Gender.MALE);
    Person jon = new Person("jon", "snow", Gender.MALE);

    /**
     * Save a single entity and verify that a key for the given keyspace/prefix exists. <br />
     * Print out the hash structure within Redis.
     */
    @Test
    public void saveSingleEntity() {

        repository.save(eddard);
        repository.save(robb);
        repository.save(jon);

        assertThat(operations
                .execute((RedisConnection connection) -> connection.exists(("persons:" + eddard.getId()).getBytes(CHARSET))))
                .isTrue();
    }
}
