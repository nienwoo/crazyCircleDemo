package com.crazymakercircle.redis;

import com.crazymakercircle.im.common.bean.User;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public interface UserDAO {
    User getUser(long id);

    void saveUser(final User user);
}
