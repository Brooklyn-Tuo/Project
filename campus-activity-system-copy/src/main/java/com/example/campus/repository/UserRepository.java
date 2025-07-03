package com.example.campus.repository;

import com.example.campus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// 用户仓库接口
// 继承 JpaRepository<User, Long> 获得基本CRUD操作
public interface UserRepository extends JpaRepository<User, Long> {

    // 通过用户名查找用户
    // 用途：登录验证和注册时检查用户名是否已存在
    User findByUsername(String username);
}