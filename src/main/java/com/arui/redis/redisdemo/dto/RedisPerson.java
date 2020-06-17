package com.arui.redis.redisdemo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisPerson {
    private String Name;
    private int Age;

}