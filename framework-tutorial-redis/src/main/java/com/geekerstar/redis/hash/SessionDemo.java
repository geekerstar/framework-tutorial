package com.geekerstar.redis.hash;

import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 用户会话管理案例
 */
public class SessionDemo {

    private Jedis jedis = new Jedis("127.0.0.1");

    /**
     * 检查session是否有效
     * @return
     */
    public boolean isSessionValid(String token) throws Exception {
        // 校验token是否为空
        if(token == null || "".equals(token)) {
            return false;
        }

        // 这里拿到的session可能就是一个json字符串
        // 我们这里简化一下，就放一个用户user_id作为这里的value
        String session = jedis.hget("sessions", "session::" + token);
        if(session == null || "".equals(session)) {
            return false;
        }

        // 检查一下这个session是否在有效期内
        String expireTime = jedis.hget("sessions::expire_time",
                "session::" + token);
        if(expireTime == null || "".equals(expireTime)) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Date expireTimeDate = dateFormat.parse(expireTime);

        Date now = new Date();

        if(now.after(expireTimeDate)) {
            return false;
        }

        // 如果token不为空，而且获取到的session不为空，而且session没过期
        // 此时可以认为session在有效期内
        return true;
    }

    /**
     * 模拟的登录方法
     * @param username
     * @param password
     * @return
     */
    public String login(String username, String password) {
        // 基于用户名和密码去登录
        System.out.println("基于用户名和密码登录：" + username + ", " + password);
        Random random = new Random();
        long userId = random.nextInt() * 100;
        // 登录成功之后，生成一块令牌
        String token = UUID.randomUUID().toString().replace("-", "");
        // 基于令牌和用户id去初始化用户的session
        initSession(userId, token);
        // 返回这个令牌给用户
        return token;
    }

    /**
     * 用户登录成功之后，初始化一个session
     * @param userId
     * @param token
     */
    public void initSession(long userId, String token) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 24);
        Date expireTime = calendar.getTime();

        jedis.hset("sessions",
                "session::" + token, String.valueOf(userId));
        jedis.hset("sessions::expire_time",
                "session::" + token, dateFormat.format(expireTime));
    }

    public static void main(String[] args) throws Exception {
        SessionDemo demo = new SessionDemo();

        // 第一次访问系统，token都是空的
        boolean isSessionValid = demo.isSessionValid(null);
        System.out.println("第一次访问系统的session校验结果：" + (isSessionValid == true ? "通过" : "不通过"));

        // 强制性进行登录，获取到token
        String token = demo.login("zhangsan","123456");
        System.out.println("登陆过后拿到令牌：" + token);

        // 第二次再次访问系统，此时是可以访问的
        isSessionValid = demo.isSessionValid(token);
        System.out.println("第二次访问系统的session校验结果：" + (isSessionValid == true ? "通过" : "不通过"));
    }

}
