package com.example.campus.service;

import com.example.campus.entity.Activity;
import com.example.campus.entity.ActivityParticipant;
import com.example.campus.repository.ActivityParticipantRepository;
import com.example.campus.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service  // 声明为Spring服务组件
public class ActivityParticipantService {

    @Autowired  // 自动注入参与记录仓库
    private ActivityParticipantRepository participantRepository;

    @Autowired  // 自动注入活动仓库
    private ActivityRepository activityRepository;

    // 报名参加活动方法 ------------------------------------------------
// 调用链: ActivityParticipantController.join() → 此方法
    public String joinActivity(Long activityId, Long userId) {
        // 1. 检查活动是否存在
        Activity activity = activityRepository.findById(activityId).orElse(null);
        if (activity == null) return "活动不存在";

        // 2. 检查是否已报名
        if (participantRepository.findByActivityIdAndUserId(activityId, userId) != null) {
            return "你已经报名过该活动";
        }

        // 3. 检查活动是否已满员
        long count = participantRepository.countByActivityId(activityId);
        if (count >= activity.getMaxParticipants()) {
            return "报名人数已满";
        }

        // 4. 检查活动是否已过期
        if (activity.getTime().isBefore(LocalDateTime.now())) {
            return "活动已结束，无法报名";
        }

        // 5. 创建新的参与记录并保存
        ActivityParticipant ap = new ActivityParticipant(activityId, userId);
        participantRepository.save(ap);
        return "报名成功";
    }

    // 获取活动参与者列表方法 --------------------------------------------
    // 调用链: ActivityParticipantController.getByActivity() → 此方法
    public List<ActivityParticipant> getParticipantsByActivity(Long activityId) {
        return participantRepository.findByActivityId(activityId);
    }

    // 取消报名方法（带事务管理）-----------------------------------------
    // 调用链: ActivityParticipantController.cancel() → 此方法
//    @Transactional  // 事务注解，确保操作原子性,,
    //不应该有这个，因为这里是取消报名的接口实现，而不是删除活动的实现，去校报名的话，就删除报名的记录就行了，没有必要删除后台的活动本身。
    public String cancelJoin(Long activityId, Long userId) {
        // 1. 检查是否已报名
        ActivityParticipant existing = participantRepository.findByActivityIdAndUserId(activityId, userId);
        if (existing == null) {
            return "你没有报名该活动";
        }

        // 2. 删除参与记录
        participantRepository.delete(existing);
        return "已成功取消报名";
    }

    // 获取用户已报名的活动列表方法 --------------------------------------
    // 调用链: ActivityParticipantController.getJoinedActivities() → 此方法
    public List<Activity> getActivitiesByUserId(Long userId) {
        // 1. 获取用户的所有参与记录
        List<ActivityParticipant> joins = participantRepository.findByUserId(userId);
        List<Activity> activities = new ArrayList<>();

        // 2. 根据参与记录查找对应的活动
        for (ActivityParticipant ap : joins) {
            activityRepository.findById(ap.getActivityId()).ifPresent(activities::add);
        }
        return activities;
    }
}