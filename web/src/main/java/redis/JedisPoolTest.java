package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Administrator on 2019/4/4/004.
 */
public class JedisPoolTest {
    public static void main(String[] args) {
        JedisPool jedisPool = RedisPoolUtils.jedisPool;

        Jedis jedis = jedisPool.getResource();

        jedis.set("home","江西");

        jedis.close();

        RedisPoolUtils.close();

    }
}
