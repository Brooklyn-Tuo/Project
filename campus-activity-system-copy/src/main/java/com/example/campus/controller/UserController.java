package com.example.campus.controller;

import com.example.campus.entity.User;
import com.example.campus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController  // RESTful控制器
@RequestMapping("/api/user")  // 基础请求路径
@CrossOrigin  // 允许跨域请求
public class UserController {

    @Autowired  // 自动注入用户服务
    private UserService userService;

    // 用户注册接口 ---------------------------------------------------------
    // 调用链: POST /api/user/register → userService.register(user)
    @PostMapping("/register")
    public String register(@RequestBody User user) {  // 接收JSON格式的用户数据
        if(user.getRole()==null){
            user.setRole("user");  // 设置默认角色
        }
        User registered = userService.register(user);
        return(registered != null) ? "注册成功" : "用户名已存在";
//        User savedUser = userService.register(user);  // 调用服务层注册用户
//        return (savedUser != null) ? "注册成功" : "用户名已存在";  // 返回注册结果
    }

    // 用户登录接口 ---------------------------------------------------------
    // 调用链: POST /api/user/login → userService.login(username, password)
    @PostMapping("/login")
    public Object login(@RequestBody User user) {  // 接收JSON格式的登录凭证
        User loggedIn = userService.login(user.getUsername(), user.getPassword());  // 调用服务层验证登录
        return (loggedIn != null) ? loggedIn : "用户名或者密码错误";  // 返回登录成功的用户信息（或null），返回整个对象而不是字符串。
    }
}