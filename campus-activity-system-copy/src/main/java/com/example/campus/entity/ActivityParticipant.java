package com.example.campus.entity;

import jakarta.persistence.*;

@Entity  // JPA实体类
@Table(name = "activity_participants",  // 映射到表"activity_participants"
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"activity_id", "user_id"})  // 唯一约束：防止重复报名
        })
public class ActivityParticipant {

    @Id  // 主键字段
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 主键自增
    private Long id;  // 参与记录ID

    @Column(name = "activity_id")  // 映射到activity_id列
    private Long activityId;  // 关联的活动ID

    @Column(name = "user_id")  // 映射到user_id列
    private Long userId;  // 参与的用户ID

    public ActivityParticipant() {}  // 无参构造方法

    // 带参构造方法（方便创建报名记录）
    public ActivityParticipant(Long activityId, Long userId) {
        this.activityId = activityId;
        this.userId = userId;
    }

    // Getter & Setter 方法 --------------------------------------------
    public Long getId() { return id; }

    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}