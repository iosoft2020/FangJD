//package com.iosoft.mall;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ProductApplicationTest {
//
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//
//    @Test
//    public void testStringRedisTemplate() {
//        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
//        valueOperations.set("hello", "world");
//        System.err.println(valueOperations.get("hello"));
//
//    }
//
//}
