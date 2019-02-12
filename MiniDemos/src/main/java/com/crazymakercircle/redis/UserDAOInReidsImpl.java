package com.crazymakercircle.redis;

import com.crazymakercircle.im.common.bean.User;
import com.crazymakercircle.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import java.io.Serializable;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class UserDAOInReidsImpl implements UserDAO {

    public static final String USER_UID_PREFIX = "user:uid:";

    @Autowired
    protected RedisTemplate<Serializable, Serializable> redisTemplate;
    private static final long CASHE_LONG = 60 * 4;//4小时
    private static final Expiration EXPIRATION_STRATEGY =
            Expiration.seconds(CASHE_LONG * 60);//4小时


    @Autowired
    public void saveUser(final User user) {
        redisTemplate.execute(new RedisCallback<Object>() {

            @Override
            public User doInRedis(RedisConnection connection)
                    throws DataAccessException {
                String key = USER_UID_PREFIX + user.getUid();
                String value = ObjectUtil.ObjectToJson(user);
                connection.set(
                        redisSerialize(key),
                        redisSerialize(value),
                        EXPIRATION_STRATEGY,
                        RedisStringCommands.SetOption.UPSERT
                );
                return user;
            }
        });
    }

    private byte[] redisSerialize(String s) {
        return redisTemplate.getStringSerializer().serialize(s);
    }

    private String redisDeSerialize(byte[] b) {
        return redisTemplate.getStringSerializer().deserialize(b);
    }

    @Override
    public User getUser(final long id) {
        return redisTemplate.execute(new RedisCallback<User>() {
            @Override
            public User doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] key = redisSerialize(USER_UID_PREFIX + id);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    String json = redisDeSerialize(value);
                    return ObjectUtil.JsonToObject(json, User.class);

                }

                return null;
            }
        });
    }

}