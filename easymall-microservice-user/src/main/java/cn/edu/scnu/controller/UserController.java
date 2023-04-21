/**
 * @Author: Neo
 * @Date: 2023/04/11 星期二 16:41:01 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.controller;

import cn.edu.scnu.service.UserService;
import com.easymall.pojo.User;
import com.easymall.utils.CookieUtils;
import com.easymall.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user/manage")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/checkUserName")
    public SysResult checkUserName(String userName) {
        return userService.checkUserName(userName) == 0 ?
                SysResult.ok() : SysResult.build(201, "用户名已存在", null);

    }

    @PostMapping("/save")
    public SysResult saveUser(User user) {
        Integer a = userService.checkUserName(user.getUserName());
        if (a != 0) {
            return SysResult.build(201, "用户名已存在", null);
        }
        try {
            userService.userSave(user);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, e.getMessage(), null);
        }
    }

    @RequestMapping("/login")
    public SysResult doLogin(User user, HttpServletRequest request, HttpServletResponse response) {
        String ticket = userService.doLogin(user);
        if (!StringUtils.isEmpty(ticket)) {
            CookieUtils.setCookie(request, response, "EM_TICKET", ticket);
            return SysResult.ok();
        }
        return SysResult.build(201, "登录失败", null);
    }

    @RequestMapping("/query/{ticket}")
    public SysResult checkLoginUser(@PathVariable String ticket) {
        String userJson = userService.queryUserJson(ticket);
        if (!StringUtils.isEmpty(userJson)) {
            return SysResult.build(200, "ok", userJson);
        }
        return SysResult.build(201, "", null);
    }
}
