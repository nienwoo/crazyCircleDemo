package com.crazymakercircle.redis;

import com.crazymakercircle.im.common.bean.User;
import com.crazymakercircle.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class UserDAOWithReisImpl implements UserDAO {

    public static final String USER_UID_PREFIX = "user:uid:";
    @Autowired
    protected RedisTemplate<Serializable, Serializable> redisTemplate;
    private static final long CASHE_LONG = 60 * 4;//4小时


    @Override
    public void saveUser(final User user) {
        String key = USER_UID_PREFIX + user.getUid();
        String value = ObjectUtil.ObjectToJson(user);
        redisTemplate.opsForValue().set(key, value, CASHE_LONG, TimeUnit.MINUTES);

    }


    @Override
    public User getUser(final long id) {
        String key = USER_UID_PREFIX + id;
        String value = (String) redisTemplate.opsForValue().get(key);

        if (!StringUtils.isEmpty(value)) {
            return ObjectUtil.JsonToObject(value, User.class);
        }
        return null;
    }

}