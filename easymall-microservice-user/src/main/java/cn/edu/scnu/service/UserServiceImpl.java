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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
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
        String ticket = UUID.randomUUID().toString();
        String userJson = JSON.toJSONString(exist);
        redisTemplate.opsForValue().set(ticket, userJson, 5L, TimeUnit.MINUTES);
        return ticket;
    }

    @Override
    public String queryUserJson(String ticket) {
        String userJson;
        try {
            userJson = redisTemplate.opsForValue().get(ticket);
            return userJson;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
