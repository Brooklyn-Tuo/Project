package com.example.campus.controller;

import com.example.campus.dto.ActivityDTO;
import com.example.campus.entity.Activity;
import com.example.campus.entity.User;
import com.example.campus.repository.ActivityRepository;
import com.example.campus.repository.UserRepository;
import com.example.campus.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController  // 声明为RESTful控制器
@RequestMapping("/api/activity")  // 基础请求路径
@CrossOrigin  // 允许跨域请求

public class ActivityController {

    @Autowired  // 自动注入活动服务
    private ActivityService activityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired  // 自动注入活动仓库（通常应在Service层使用）
    private ActivityRepository activityRepository;

    // 获取所有活动列表接口 -------------------------------------------------
    // 调用链: GET /api/activity/list → activityService.getAllActivities()
    @GetMapping("/list")
    public List<Activity> list(){
        return activityService.getAllActivities();  // 调用服务层获取活动列表
    }

    // 创建新活动接口 -------------------------------------------------------
    // 调用链: POST /api/activity/create → activityService.createActivity(activity)
    @PostMapping("/create")
    public String create(@RequestBody Activity activity) {  // 接收JSON格式的活动数据
        Activity saved = activityService.createActivity(activity);  // 调用服务层创建活动
        return (saved != null) ? "活动发布成功" : "发布失败";  // 返回操作结果
    }

    // ✅ 获取当前用户报名的所有活动列表
    @GetMapping("/joined")
    public List<Activity> getJoinedActivities(@RequestParam Long userId) {
        return activityService.getJoinedActivities(userId);
    }


    // 获取用户发布的活动接口 -------------------------------------------------
    // 调用链: GET /api/activity/byUserId → activityService.getActivitiesByUser(userId)
    @GetMapping("/byUserId")
    public List<Activity> getByUser(@RequestParam Long userId) {  // 从请求参数获取用户ID
        return activityService.getActivitiesByUser(userId);  // 调用服务层获取用户活动
    }

    // 获取活动详情接口 -----------------------------------------------------
    // 调用链: GET /api/activity/{id} → activityService.getActivityById(id)
    @GetMapping("/{id}")
    public Activity detail(@PathVariable Long id){  // 从路径获取活动ID
        return activityService.getActivityById(id);  // 调用服务层获取活动详情
    }

    // 删除活动接口（问题接口）----------------------------------------------
    // 调用链: DELETE /api/activity/delete/{id} → activityRepository.deleteById(id)
    // ⚠️ 问题点：直接调用Repository而非Service层，可能导致事务问题
    @Transactional
    @DeleteMapping("/delete/{id}")
    public String deleteActivity(@PathVariable Long id) {  // 从路径获取活动ID
        activityRepository.deleteById(id);  // 直接调用仓库层删除方法
        return "活动已删除";  // 总是返回成功消息（可能未实际删除）
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String,Object>> searchActivities(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size)
    {
        List<ActivityDTO> activities = activityService.getActivities(keyword, page, size);
        long total = activityRepository.count(); // 这里你可以根据需求使用分页总数

        Map<String, Object> result = new HashMap<>();
        result.put("content", activities);
        result.put("totalPages", (int) Math.ceil((double) total / size));

        return ResponseEntity.ok(result);
    }

    @GetMapping("/grouped")
    public Map<String, List<Activity>> getGroupedActivities() {
        List<Activity> all = activityRepository.findAll();
        Map<String, List<Activity>> grouped = new LinkedHashMap<>();

        for (Activity activity : all) {
            // ⚠️ 关键：从 createdBy 拿到对应用户名
            User user = userRepository.findById(activity.getCreatedBy()).orElse(null);
            String username = (user != null) ? user.getUsername() : "未知发布者";
            activity.setCreatedByUsername(username); // 这一步必须有！！

            // 分组
            grouped.computeIfAbsent(username, k -> new ArrayList<>()).add(activity);
        }

        return grouped;
    }

    @GetMapping("/admin/created")
    public List<Activity> getActivitiesByAdmin(@RequestParam Long userId) {
        return activityRepository.findByCreatedBy(userId);
    }









}