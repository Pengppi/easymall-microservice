/**
 * @Author: Neo
 * @Date: 2023/04/11 星期二 16:45:57 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.service;

import cn.edu.scnu.mapper.UserMapper;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easymall.pojo.User;
import com.easymall.utils.MD5Util;
import com.easymall.utils.PrefixKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Integer checkUserName(String userName) {
        return userMapper.queryUserName(userName);
    }

    @Override
    public void userSave(User user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        this.save(user);
    }

    @Override
    public String doLogin(User user) {
        user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        User exist = userMapper.queryUserByNameAndPassword(user);
        if (exist == null) {
            return "";
        }
        String ticket = PrefixKey.USER_LOGIN_TICKET + user.getUserId() + System.currentTimeMillis();
        String userJson = JSON.toJSONString(exist);
        //最多1个用户同时登录
        //判断当前登录用户是否已登录过
        String loginCheckKey = PrefixKey.USER_LOGINED_CHECK_PREFIX + exist.getUserId();
        String oldTicket = redisTemplate.opsForValue().get(loginCheckKey);
        if (!StringUtils.isEmpty(oldTicket)) {
            //已登录过，删除旧的ticket
            redisTemplate.delete(oldTicket);
        }
        redisTemplate.opsForValue().set(loginCheckKey, ticket);
        /* //最多3个用户同时登录
        Long size = redisTemplate.opsForList().size(exist.getUserId());
        if (size != null && size >= 3) {
            //已登录过，删除旧的ticket
            String oldTicket = redisTemplate.opsForList().leftPop(exist.getUserId());
            redisTemplate.delete(oldTicket);
            redisTemplate.opsForList().rightPush(exist.getUserId(), ticket);
        } else {
            redisTemplate.opsForList().rightPush(exist.getUserId(), ticket);
        } */
        //将用户信息存入redis
        log.info("user: " + exist.getUserName() + " login, ticket: " + ticket);
        redisTemplate.opsForValue().set(ticket, userJson, 15L, TimeUnit.MINUTES);
        return ticket;
    }

    @Override
    public String queryUserJson(String ticket) {
        String userJson;
        try {
            Long leftTime = redisTemplate.getExpire(ticket, TimeUnit.SECONDS);
            userJson = redisTemplate.opsForValue().get(ticket);
            if (leftTime < 60 * 5) {
                log.info("ticket: " + ticket + " is about to expire, left time: " + leftTime + " seconds, renewing...");
                redisTemplate.expire(ticket, leftTime + 60 * 10, TimeUnit.SECONDS);
            }
            return userJson;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
