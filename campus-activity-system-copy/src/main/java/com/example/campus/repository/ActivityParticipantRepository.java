package com.example.campus.repository;

import com.example.campus.entity.ActivityParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// 活动参与记录仓库接口
// 继承 JpaRepository<ActivityParticipant, Long> 获得基本CRUD操作
public interface ActivityParticipantRepository extends JpaRepository<ActivityParticipant, Long> {

    // 通过活动ID和用户ID查找参与记录
    // 用途：检查用户是否已报名某活动
    ActivityParticipant findByActivityIdAndUserId(Long activityId, Long userId);

    // 统计指定活动的参与人数
    // 用途：检查活动是否已满员
    long countByActivityId(Long activityId);

    // 查找指定活动的所有参与记录
    // 用途：获取活动参与者列表
    List<ActivityParticipant> findByActivityId(Long activityId);

    // 删除指定活动和用户的参与记录
    // 用途：取消报名（无返回值）
    void deleteByActivityIdAndUserId(Long activityId, Long userId);

    // 查找用户的所有参与记录
    // 用途：获取用户报名的活动列表
    List<ActivityParticipant> findByUserId(Long userId);


}