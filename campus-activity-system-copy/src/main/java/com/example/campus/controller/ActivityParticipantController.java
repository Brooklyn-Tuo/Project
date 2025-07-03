package com.example.campus.controller;

import com.example.campus.entity.Activity;
import com.example.campus.entity.ActivityParticipant;
import com.example.campus.repository.ActivityParticipantRepository;
import com.example.campus.service.ActivityParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController  // RESTful控制器
@RequestMapping("/api/participant")  // 基础请求路径
@CrossOrigin  // 允许跨域请求
public class ActivityParticipantController {

    @Autowired  // 自动注入参与服务
    private ActivityParticipantService service;
    @Autowired  // 自动注入活动服务
    private ActivityParticipantRepository participantRepository;


    // 报名参加活动接口 ------------------------------------------------------
    // 调用链: POST /api/participant/join → service.joinActivity(activityId, userId)
    @PostMapping("/join")
    public String join(@RequestBody Map<String, Long> data) {  // 接收JSON格式的报名数据
        Long activityId = data.get("activityId");  // 从请求体获取活动ID
        Long userId = data.get("userId");  // 从请求体获取用户ID
        return service.joinActivity(activityId, userId);  // 调用服务层处理报名
    }

    // 获取活动参与者列表接口 -------------------------------------------------
    // 调用链: GET /api/participant/byActivityId → service.getParticipantsByActivity(activityId)
    @GetMapping("/byActivityId")
    public List<ActivityParticipant> getByActivity(@RequestParam Long activityId) {  // 从请求参数获取活动ID
        return service.getParticipantsByActivity(activityId);  // 调用服务层获取参与者列表
    }

    // 取消报名接口 ---------------------------------------------------------
    // 调用链: DELETE /api/participant/cancel → service.cancelJoin(activityId, userId)
    @DeleteMapping("/cancel")
    public String cancel(@RequestParam Long activityId, @RequestParam Long userId) {  // 从请求参数获取活动ID和用户ID
        return service.cancelJoin(activityId, userId);  // 调用服务层取消报名
    }

    // 获取用户已报名的活动接口 -----------------------------------------------
    // 调用链: GET /api/participant/my-joined → service.getActivitiesByUserId(userId)
    @GetMapping("/my-joined")
    public List<Activity> getJoinedActivities(@RequestParam Long userId) {  // 从请求参数获取用户ID
        return service.getActivitiesByUserId(userId);  // 调用服务层获取用户参与的活动
    }

    // 获取某活动当前已报名人数
    @GetMapping("/count")
    public Long countParticipants(@RequestParam Long activityId) {
        return participantRepository.countByActivityId(activityId);
    }
    //判断是否已经报名的接口
    @GetMapping("/hasJoined")
    public boolean hasJoined(@RequestParam Long activityId, @RequestParam Long userId) {
        return participantRepository.findByActivityIdAndUserId(activityId, userId) != null;
    }

    @GetMapping("/joined-ids")
    public List<Long> getJoinedActivityIds(@RequestParam Long userId) {
        List<ActivityParticipant> joined = participantRepository.findByUserId(userId);
        return joined.stream().map(ActivityParticipant::getActivityId).toList();
    }




}