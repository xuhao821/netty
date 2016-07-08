package com.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ian on 2016/7/5.
 */
public class CollectionRedis {
    JedisPool pool;
    Jedis jedis;

    @Before
    public void setUp() {
        pool = new JedisPool(new JedisPoolConfig(), "localhost");
        jedis = pool.getResource();
    }

    @Test
    public void map_redis(){
        Map<String, String> map = new HashMap<String, String>();
            map.put("name", "fujianchao");
            map.put("password", "123");
            map.put("age", "12");
        // 存入一个map
            jedis.hmset("user", map);
        // map key的个数
        System.out.println("map的key的个数" + jedis.hlen("user"));
        // map key
        System.out.println("map的key" + jedis.hkeys("user"));
        // map value
        System.out.println("map的value" + jedis.hvals("user"));
        // (String key, String... fields)返回值是一个list
        List<String> list = jedis.hmget("user", "age", "name");
        System.out.println("redis中key的各个 fields值："
                + jedis.hmget("user", "age", "name") + list.size());
        // 删除map中的某一个键 的值 password
        // 当然 (key, fields) 也可以是多个fields
        jedis.hdel("user", "age");
        System.out.println("删除后map的key" + jedis.hkeys("user"));
    }
    @Test
    public void list_redis(){
        System.out.println(jedis.lrange("mylist",0,4));
    }
}
