package cn.edu.scnu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easymall.pojo.User;

public interface UserService extends IService<User> {
    Integer checkUserName(String userName);

    void userSave(User user);

    String doLogin(User user);

    String queryUserJson(String ticket);
}
