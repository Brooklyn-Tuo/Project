package com.example.campus.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity  // 声明为JPA实体类
@Table(name = "activities")  // 映射到数据库表"activities"
public class Activity {

    @Id  // 主键字段
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 主键自增策略
    private Long id;  // 活动ID

    private String title;  // 活动标题

    private String description;  // 活动描述

    @Column(nullable = false)
    private LocalDateTime time;  // 活动时间

    private int maxParticipants;  // 最大参与人数

    @Column(name = "created_by")  // 映射到created_by列
    private Long createdBy;  // 发布者用户ID


    public Activity() {}  // JPA要求的无参构造方法

    @Transient
    private String createdByUsername;

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public void setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
    }




    // Getter & Setter 方法 --------------------------------------------
    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getTime() { return time; }
    public void setTime(LocalDateTime time) { this.time = time; }

    public int getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}