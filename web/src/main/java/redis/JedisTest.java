package redis;

import redis.clients.jedis.Jedis;

/**
 * 测试jedis
 * Created by Administrator on 2019/4/3/003.
 */
public class JedisTest {
    public static void main(String[] args) {
        Jedis jedis=new Jedis("127.0.0.1",6379);

        jedis.set("name","wuxinxin");
        jedis.set("age",18+"");

        jedis.close();
    }
}
