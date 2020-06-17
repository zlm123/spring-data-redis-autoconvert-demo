package com.arui.redis.redisdemo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arui.redis.redisdemo.dto.Person;
import com.arui.redis.redisdemo.dto.RedisPerson;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Service
public class RedisServices {
    // inject the actual template
    @Autowired
    private RedisTemplate<String, RedisPerson> redisTemplate1;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate2;
//    @Autowired
//    private RedisTemplate<String, Integer> template2;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    @Resource(name = "redisTemplate")
//    private RedisTemplate redisTemplate2;

    // inject the template as ListOperations
    @Autowired
    private HashOperations<String, String, Object> hashOps;
    @Autowired
    RedisPerson person;

//    public void setString() {
//        stringRedisTemplate.opsForValue().set("a", "hi");
//    }

    public void setObj() {
//        person.setName("arui");
//        person.setAge(35);
        redisTemplate1.opsForHash().put("aaa", "123", person);


        RedisPerson re = (RedisPerson) redisTemplate1.opsForHash().get("aaa", "123");
        redisTemplate2.opsForHash().put("aaa1", "123", person);
        RedisPerson re2 = (RedisPerson)  redisTemplate2.opsForHash().get("aaa1", "123");
        RedisPerson redisPerson = JSONObject.parseObject(JSON.toJSONString(redisTemplate1.opsForHash().get("aaa", "123")), RedisPerson.class);
        System.out.println(re);
        System.out.println(re2);

        RedisPerson redisPerson2 = JSONObject.parseObject(JSON.toJSONString(redisTemplate1.opsForHash().get("aaa", "123")), RedisPerson.class);
        System.out.println(redisPerson2);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("arui", "aaa");
        hashOps.put("aaa1", "123", person);
        hashOps.putAll("aa3", map);
        hashOps.put("aa3","arui1","jj");
//        Object re = listOps.get("aaa1", "123");

//        JSONObject.parse( ,RedisPerson.class)
//        System.out.println(redisPerson);
//        Person person = JSONObject.parseObject(template.opsForHash().get("aaa", "123"), Person.class);
//        System.out.println(person);

//        listOps.leftPush("aaa"
//                , String.valueOf(person));
//        person.setName("arui");
//        person.setAge(88);
//        template.opsForValue().set("a", "person");
//        template.opsForList().leftPush("aaa",person);
//        template.opsForHash().put("aa1","13",person);
//        template.opsForHash().put("aa1","14",100);
//        template2.opsForHash().put("aa2","13",6);
//        redisTemplate.opsForValue().set("aaa", "aaa");
//        redisTemplate.opsForList().leftPush("aaa",String.valueOf(person));
//       String re= redisTemplate.opsForList().leftPop("aaa");
//        System.out.println(re);
//        redisTemplate.opsForList().leftPush("bbb","bbb");
//        listOps.leftPush("ccc","ccc");
//        redisTemplate.opsForList().leftPush("aaa",  "aaa");
//        redisTemplate2.opsForList().leftPush("aaaa","aaa");

    }

    public void setObj2() {

    }


}
