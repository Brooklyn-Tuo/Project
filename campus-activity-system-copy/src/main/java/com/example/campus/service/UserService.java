package com.example.campus.service;

import com.example.campus.entity.User;
import com.example.campus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service  // 声明为Spring服务组件
public class UserService {

    @Autowired  // 自动注入用户仓库
    private UserRepository userRepository;

    // 用户注册方法 ------------------------------------------------
    // 调用链: UserController.register() → 此方法
    public User register(User user) {
        // 检查用户名是否已存在
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return null;  // 用户已存在
        }

        // 设置默认角色（根据需求调整）
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("user");  // 默认角色
        }

        return userRepository.save(user);  // 保存新用户
    }

    // 用户登录方法 ------------------------------------------------
    // 调用链: UserController.login() → 此方法
    public User login(String username, String password) {
        // 1. 根据用户名查找用户
        User user = userRepository.findByUsername(username);

        // 2. 验证密码
        if (user != null && user.getPassword().equals(password)) {
            return user;  // 登录成功
        }
        return null;  // 登录失败
    }


}