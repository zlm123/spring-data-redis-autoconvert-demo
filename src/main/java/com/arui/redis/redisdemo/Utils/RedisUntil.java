package com.arui.redis.redisdemo.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

/**
 * @Author: ARui
 * @Date: 2020/6/17 15:02
 */
@Component
public class RedisUntil {
    @Autowired
    private HashOperations<String, String, Object> hashOps;

    public <T> T getRedisHashObj(String hashKey, String rowKey) {
        return (T) hashOps.get(hashKey, rowKey);
    }

}
