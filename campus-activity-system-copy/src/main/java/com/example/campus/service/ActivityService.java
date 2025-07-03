package com.example.campus.service;

import com.example.campus.dto.ActivityDTO;
import com.example.campus.entity.Activity;
import com.example.campus.entity.ActivityParticipant;
import com.example.campus.entity.User;
import com.example.campus.repository.ActivityParticipantRepository;
import com.example.campus.repository.ActivityRepository;
import com.example.campus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ActivityParticipantRepository participantRepository;
    @Autowired
    private UserRepository userRepository;



    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Activity getActivityById(Long id) {
        Optional<Activity> activity = activityRepository.findById(id);
        return activity.orElse(null);
    }
    public List<Activity> getActivitiesByUser(Long userId) {
        return activityRepository.findByCreatedBy(userId);
    }

    public List<Activity> getJoinedActivities(Long userId) {
        List<ActivityParticipant> joins = participantRepository.findByUserId(userId);
        List<Long> ids = joins.stream().map(ActivityParticipant::getActivityId).toList();
        return activityRepository.findAllById(ids);
    }


    public List<ActivityDTO> getActivities(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "time"));

        Page<Activity> pageData;
        if (keyword == null || keyword.isEmpty()) {
            pageData = activityRepository.findAll(pageable);
        } else {
            pageData = activityRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        }

        // 将 Activity 转换为 ActivityDTO，添加 currentParticipants
        return pageData.getContent().stream().map(activity -> {
            long currentCount = participantRepository.countByActivityId(activity.getId());
            return new ActivityDTO(
                    activity.getId(),
                    activity.getTitle(),
                    activity.getDescription(),
                    activity.getTime(),
                    activity.getMaxParticipants(),
                    activity.getCreatedBy(),
                    currentCount
            );
        }).collect(Collectors.toList());
    }

    // 查询所有活动，按发布者分组
    // ActivityService.java
    public Map<String, List<Activity>> getActivitiesGroupedByAdmin() {
        List<Activity> allActivities = activityRepository.findAll();
        Map<String, List<Activity>> grouped = new HashMap<>();

        for (Activity activity : allActivities) {
            User user = userRepository.findById(activity.getCreatedBy()).orElse(null);
            if (user != null) {
                activity.setCreatedByUsername(user.getUsername()); // 设置社团名
                grouped.computeIfAbsent(user.getUsername(), k -> new ArrayList<>()).add(activity);
            }
        }

        return grouped;
    }





}
