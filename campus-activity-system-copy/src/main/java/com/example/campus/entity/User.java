package com.example.campus.entity;

import jakarta.persistence.*;

@Entity  // JPA实体类
@Table(name = "users")  // 映射到表"users"（避免使用SQL关键字）
public class User {

    @Id  // 主键字段
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 主键自增
    private Long id;  // 用户ID

    @Column(nullable = false, unique = true)  // 非空且唯一
    private String username;  // 用户名

    @Column(nullable = false)  // 非空
    private String password;  // 密码

    @Column(nullable = false)  // 非空
    private String role;  // 角色：student（学生）或 club（社团）admin（管理员）

    public User() {}  // 无参构造方法

    // 带参构造方法（方便创建用户）
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter & Setter 方法 --------------------------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() { return role; }
    public void setRole(String role) {
        this.role = role;
    }
}