package com.example.campus.repository;

import com.example.campus.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// 活动仓库接口
// 继承 JpaRepository<Activity, Long> 获得基本CRUD操作
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    // 自定义查询：通过发布者ID查找活动
    // 用途：获取用户发布的活动列表
    // 方法名解析：findBy + 实体字段名（createdBy）
    List<Activity> findByCreatedBy(Long createdBy);

    Page<Activity> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);


}


