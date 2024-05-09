package jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Jedis_ {

    //连接redis
    //1. 如果redis设置了密码，则需要进行身份验证
    //2. 打开防火墙端口
    //3. 开启远程连接
    @Test
    public void con() {
        Jedis jedis = new Jedis("192.168.220.200", 6379);
        jedis.auth("123456");
        String res = jedis.ping();
        System.out.println("连接成功，返回结果 = " + res);
        jedis.close();//关闭当前连接，redis正常运行
    }

    //key操作
    @Test
    public void key() {
        Jedis jedis = new Jedis("192.168.220.200", 6379);
        jedis.auth("123456");
        jedis.set("k01", "v01");
        jedis.set("k02", "v02");
        jedis.set("k03", "v03");
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println("key==>" + key);
        }
        //判断key是否存在，ttl
        System.out.println(jedis.exists("k01"));
        //判断key是否过期
        System.out.println(jedis.ttl("k01"));
        //获取具体的值
        System.out.println(jedis.get("k01"));
        //关闭连接
        jedis.close();
    }

    //string
    @Test
    public void string() {
        Jedis jedis = new Jedis("192.168.220.200", 6379);
        jedis.auth("123456");
        jedis.mset("k1", "zuke1", "k2", "zuke2", "k3", "zuke3");//批量设置k-v
        List<String> mget = jedis.mget("k1", "k2");
        for (String s : mget) {
            System.out.println("s==>" + s);
        }
        jedis.close();
    }

    //list
    @Test
    public void list() {
        Jedis jedis = new Jedis("192.168.220.200", 6379);
        jedis.auth("123456");
        jedis.lpush("name_list", "zuke1", "zuke2", "zuke3");
        List<String> nameList = jedis.lrange("name_list", 0, -1);
        for (String s : nameList) {
            System.out.println("s==>" + s);
        }
        jedis.close();
    }

    //set
    @Test
    public void set() {
        Jedis jedis = new Jedis("192.168.220.200", 6379);
        jedis.auth("123456");
        jedis.sadd("city", "云南", "上海");
        jedis.sadd("city", "广州");
        jedis.sadd("city", "深圳");
        Set<String> city = jedis.smembers("city");
        for (String s : city) {
            System.out.println("s==>" + s);
        }
        jedis.close();
    }

    //hash
    @Test
    public void hash() {
        Jedis jedis = new Jedis("192.168.220.200", 6379);
        jedis.auth("123456");
        //方式一
        jedis.hset("hash01", "name", "李白");
        jedis.hset("hash01", "age", "30");
        String name = jedis.hget("hash01", "name");
        System.out.println(name);
        //方式二
        System.out.println("==========================");
        HashMap<String, String> map = new HashMap<>();
        map.put("job", "Java工程师");
        map.put("name", "zuke");
        map.put("email", "123@163.com");
        jedis.hset("hash03", map);
        List<String> people = jedis.hmget("hash03", "job", "name", "email");
        for (String person : people) {
            System.out.println(person);
        }

        jedis.close();
    }

    //zset
    @Test
    public void zset() {
        Jedis jedis = new Jedis("192.168.220.200", 6379);
        jedis.auth("123456");
        jedis.zadd("hero", 1, "关羽");
        jedis.zadd("hero", 2, "张飞");
        jedis.zadd("hero", 3, "赵云");
        jedis.zadd("hero", 4, "马超");
        jedis.zadd("hero", 5, "黄忠");
        System.out.println("按照评分从小到大：");
        Set<String> hero = jedis.zrange("hero", 0, -1);
        for (String s : hero) {
            System.out.println(s);
        }
        System.out.println("按照评分从大到小：");
        Set<String> hero2 = jedis.zrevrange("hero", 0, -1);
        for (String s : hero2) {
            System.out.println(s);
        }
        jedis.close();
    }

}
