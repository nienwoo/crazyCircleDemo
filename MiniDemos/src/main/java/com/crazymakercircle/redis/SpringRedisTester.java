package com.crazymakercircle.redis;

import com.crazymakercircle.im.common.bean.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class SpringRedisTester {


    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-redis.xml");
        UserDAO userDAO = (UserDAO) ac.getBean("userDAO");
        User user1 = new User();
        user1.setUid("1");
        user1.setNickName("obama");
        userDAO.saveUser(user1);
        User user2 = userDAO.getUser(1);
        System.out.println(user2.getNickName());
    }
}
