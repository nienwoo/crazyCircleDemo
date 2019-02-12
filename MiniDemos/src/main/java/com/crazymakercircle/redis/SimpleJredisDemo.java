package com.crazymakercircle.redis;

import org.junit.Test;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class SimpleJredisDemo {

    /**
     * Redis 字符串数据类型的相关命令用于管理 redis 字符串值
     */
    @Test
    public void testString() {
        Jedis conn = new Jedis("localhost");
        System.out.println(conn.ping());//pang 代表链接成功

        conn.set("test", "123");//save key test val 123
        System.out.println(conn.get("test"));//get key
        System.out.println(conn.exists("test"));// key是否存在
        System.out.println(conn.strlen("test"));//返回key的长度
        System.out.println(conn.getrange("test", 0, -1));//返回截取字符串长度 0 -1 截取全部
        System.out.println(conn.getrange("test", 1, 2));

        System.out.println(conn.append("test", "appendStr"));//追加
        System.out.println(conn.get("test"));

        conn.rename("test", "test_new");//重命名
        System.out.println(conn.exists("test"));


        conn.mset("key1", "val1", "key2", "val2");//批量插入
        System.out.println(conn.mget("key1", "key2"));//批量取出
        System.out.println(conn.del("key1"));//删除
        System.out.println(conn.exists("key1"));

        System.out.println(conn.getSet("key2", "2"));//取出旧值 并set新值
        System.out.println(conn.incr("key2")); //自增1 要求数值类型
        System.out.println(conn.incrBy("key2", 5));//自增5 要求数值类型
        System.out.println(conn.decr("key2"));//自减1 要求数值类型
        System.out.println(conn.decrBy("key2", 5));
        System.out.println(conn.incrByFloat("key2", 1.1));//增加浮点类型

        System.out.println(conn.setnx("key2", "existVal"));//返回0 只有在key不存在的时候才设置
        System.out.println(conn.get("key2"));// 3.1

        System.out.println(conn.msetnx("key2", "exists1", "key3", "exists2"));//只有key都不存在的时候才设置
        System.out.println(conn.mget("key2", "key3"));// null
//设置key 3 秒后失效
        conn.setex("key4", 3, "3 seconds is no Val");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(conn.get("key4"));// 3 seconds is no Val


        conn.set("key6", "123456789");
        //从第三位开始 将值覆盖 下标从0 开始
        conn.setrange("key6", 3, "abcdefg");
        System.out.println(conn.get("key6"));//123abcdefg

        //返回数据类型  string
        System.out.println(conn.type("key6"));
        conn.close();

    }


    /**
     * Redis列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边）
     * 一个列表最多可以包含 232 - 1 个元素 (4294967295, 每个列表超过40亿个元素)
     */
    @Test
    public void testList() {
        Jedis conn = new Jedis("localhost");
        System.out.println(conn.ping());
//向list添加元素
        conn.lpush("list1", "yz", "wc", "xzg");

        //遍历元素
        System.out.println(conn.lrange("list1", 0, -1));
        //获取元素长度
        System.out.println(conn.llen("list1"));
        //获取 下面 1 的元素
        System.out.println(conn.lindex("list1", 1));
        //左侧弹出元素
        System.out.println(conn.lpop("list1"));
        //右侧弹出元素
        System.out.println(conn.rpop("list1"));
        //设置下面0的元素val
        conn.lset("list1", 0, "hxx");

        //如果 list1 存在 则set值
        conn.rpushx("list1", "yz");
        //在list1 的 yz 后面插入 wc
        conn.linsert("list1", BinaryClient.LIST_POSITION.AFTER, "yz", "wc");
        //头部添加
        conn.lpush("list1", "yz");
        //移除 数量count(=0 所有 >0 从头部(左侧) <) 从尾部)为 yz的元素
        conn.lrem("list1", 2, "yz");

        //按下面裁剪集合 返回 范围内的 元素
        conn.ltrim("list1", 1, -1);

        conn.lpush("list2", "cs1", "cs2");
        System.out.println(conn.lrange("list1", 0, -1));//[hxx, xzg, wc]
        System.out.println(conn.lrange("list2", 0, -1));//[cs2, cs1]
        //集合list1 右侧弹出元素 加入 list2 左侧
        conn.rpoplpush("list1", "list2");
        System.out.println(conn.lrange("list1", 0, -1));//[hxx, xzg]
        System.out.println(conn.lrange("list2", 0, -1));//[wc, cs2, cs1]

        System.out.println(conn.type("list1"));

        conn.close();
    }


    /**
     * Redis 的 Set 是 String 类型的无序集合。集合成员是唯一的，这就意味着集合中不能出现重复的数据。
     * Redis 中集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是 O(1)。
     * 集合中最大的成员数为 232 - 1 (4294967295, 每个集合可存储40多亿个成员)。
     */
    @Test
    public static void testSet() {
        Jedis conn = new Jedis("localhost");
        System.out.println(conn.ping());

        conn.sadd("set1", "yz", "zwl", "xzg", "hxx", "xzg"); //像集合添加元素
        System.out.println(conn.smembers("set1"));//遍历所有元素
        System.out.println(conn.scard("set1"));//获取集合元素个数
        System.out.println(conn.sismember("set1", "hxx"));//判断是否是集合元素
        System.out.println(conn.spop("set1"));//随机弹出一个元素
        System.out.println(conn.srandmember("set1", 2));//随机返回 2 个元素 并不会从集合中移除
        System.out.println(conn.srem("set1", "yz", "zwl"));//移除元素

        conn.sadd("set2", "hxx", "yz", "zwl", "xzg");
        System.out.println(conn.sinter("set1", "set2"));//求两个集合的交集
        System.out.println(conn.sinterstore("set3", "set1", "set2"));//求两个集合的交集 并存放到set3
        System.out.println(conn.smembers("set3"));

        System.out.println(conn.sdiff("set1", "set2"));//求两个集合的差集 同时也存在  conn.sdiffstore()
        System.out.println(conn.sunion("set1", "set2"));//求两个集合的并集 同时也存在  conn.sunionstore()

        System.out.println(conn.smove("set2", "set1", "yz"));//将yz 从 set2 移动dao set1 同时set2 中yz 移除
        System.out.println(conn.type("set1"));
        conn.close();
    }


    /**
     * Redis hash 是一个string类型的field和value的映射表，hash特别适合用于存储对象。
     * Redis 中每个 hash 可以存储 232 - 1 键值对（40多亿）
     */
    @Test
    public void testHash() {

        Jedis conn = new Jedis("localhost");
        conn.hset("student", "name", "yz");
        System.out.println(conn.hget("student", "name"));

        Map<String, String> studentFields = new HashMap<String, String>();
        studentFields.put("score", "56");
        studentFields.put("addr", "gaoxin");
        conn.hmset("student", studentFields);//批量添加
        System.out.println(conn.hgetAll("student"));//获取键值
        System.out.println(conn.hmget("student", "score", "name"));//批量获取key中field对应的val

        conn.hincrByFloat("student", "score", 1.2);// 同 String
        System.out.println(conn.hget("student", "score"));

        System.out.println(conn.hkeys("student"));//获取所有的key
        System.out.println(conn.hvals("student"));//获取所有的val

        System.out.println(conn.hlen("student"));//获取长度
        System.out.println(conn.hexists("student", "name"));//判断是否存在

        conn.hdel("student", "addr");//删除一个field
        System.out.println(conn.hexists("student", "addr"));

        conn.hsetnx("student", "addr", "hefei");//不存在 即设置
        System.out.println(conn.hget("student", "addr"));

        System.out.println(conn.type("student"));//hash
        conn.close();
    }

    /**
     * Redis 有序集合和集合一样也是string类型元素的集合,且不允许重复的成员。
     * 不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
     * 有序集合的成员是唯一的,但分数(score)却可以重复。
     * 集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。 集合中最大的成员数为 232 - 1 (4294967295, 每个集合可存储40多亿个成员)。
     */
    @Test
    public void testZset() {

        Jedis conn = new Jedis("localhost");
        System.out.println(conn.ping());

        Map<String, Double> members = new HashMap<String, Double>();
        members.put("yz", 12.0);
        members.put("zwl", 67.5);
        members.put("xzg", 34.0);
        conn.zadd("zset1", members);//添加元素

        System.out.println(conn.zcard("zset1"));//获取集合元素个数
        System.out.println(conn.zrange("zset1", 0, -1));//按照下标起始终止遍历元素
        System.out.println(conn.zrangeByScore("zset1", 30, 60));//按照分数起始终止遍历元素
        Set<Tuple> res0 = conn.zrangeByScoreWithScores("zset1", 30, 60);//按照分数起始终止遍历元素 返回 field score
        for (Tuple temp : res0) {
            System.out.println(temp.getElement() + " : " + temp.getScore());
        }
        System.out.println(conn.zrevrange("zset1", 0, -1));//按照下标起始终止倒序遍历元素
        System.out.println(conn.zrevrangeByScore("zset1", 60, 0));//按照分数起始终止倒序遍历元素

        System.out.println(conn.zscore("zset1", "yz"));//获取元素score值
        System.out.println(conn.zcount("zset1", 0, 60));//获取元素分数区间的元素

        System.out.println(conn.zrank("zset1", "yz"));//获取元素下标
        System.out.println(conn.zrevrank("zset1", "yz"));//倒序获取元素下标
        System.out.println(conn.zrem("zset1", "yz", "zwl"));//删除元素
        System.out.println(conn.zremrangeByRank("zset3", 0, -1));//删除元素通过下标范围
        System.out.println(conn.zremrangeByScore("zset4", 0, 10));//删除元素通过分数范围
        System.out.println(conn.zrange("zset1", 0, -1));

        Map<String, Double> members2 = new HashMap<String, Double>();
        members2.put("yz", 36.1);
        members2.put("zwl", 12.5);
        members2.put("xzg", 24.0);
        conn.zadd("zset2", members2);//添加元素
        System.out.println(conn.zincrby("zset1", 1, "yz"));//增加指定分数
        Set<Tuple> rs = conn.zrangeWithScores("zset1", 0, -1);
        for (Tuple temp : rs) {
            System.out.println(temp.getElement() + " : " + temp.getScore());
        }
        System.out.println("===================================");
        Set<Tuple> rs2 = conn.zrangeWithScores("zset2", 0, -1);
        for (Tuple temp : rs2) {
            System.out.println(temp.getElement() + " : " + temp.getScore());
        }
        System.out.println("===================================");

        conn.zinterstore("zset3", "zset1", "zset2");
        Set<Tuple> rs3 = conn.zrangeWithScores("zset3", 0, -1);
        for (Tuple temp : rs3) {
            System.out.println(temp.getElement() + " : " + temp.getScore());
        }

        /**
         * ZUNIONSTORE destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]
         计算给定的一个或多个有序集的并集，其中给定 key 的数量必须以 numkeys 参数指定，并将该并集(结果集)储存到 destination 。
         默认情况下，结果集中某个成员的 score 值是所有给定集下该成员 score 值之 和 。
         WEIGHTS
         使用 WEIGHTS 选项，你可以为 每个 给定有序集 分别 指定一个乘法因子(multiplication factor)，
         每个给定有序集的所有成员的 score 值在传递给聚合函数(aggregation function)之前都要先乘以该有序集的因子。
         如果没有指定 WEIGHTS 选项，乘法因子默认设置为 1 。
         AGGREGATE
         使用 AGGREGATE 选项，你可以指定并集的结果集的聚合方式。
         默认使用的参数 SUM ，可以将所有集合中某个成员的 score 值之 和 作为结果集中该成员的 score 值；
         使用参数 MIN ，可以将所有集合中某个成员的 最小 score 值作为结果集中该成员的 score 值；
         而参数 MAX 则是将所有集合中某个成员的 最大 score 值作为结果集中该成员的 score 值。
         */
        conn.zunionstore("zset4", "zset1", "zset2");
        Set<Tuple> rs4 = conn.zrangeWithScores("zset4", 0, -1);
        for (Tuple temp : rs4) {
            System.out.println(temp.getElement() + " : " + temp.getScore());
        }

        System.out.println(conn.type("zset1"));//zset
        conn.close();

    }
}
