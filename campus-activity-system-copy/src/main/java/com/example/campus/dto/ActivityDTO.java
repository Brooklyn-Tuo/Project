package com.example.campus.dto;

import java.time.LocalDateTime;

public class ActivityDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime time;
    private int maxParticipants;
    private Long createdBy;
    private long currentParticipants; // ✅ 新增字段：当前已报名人数

    // 构造器
    public ActivityDTO(Long id, String title, String description, LocalDateTime time, int maxParticipants, Long createdBy, long currentParticipants) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
        this.maxParticipants = maxParticipants;
        this.createdBy = createdBy;
        this.currentParticipants = currentParticipants;
    }

    // Getter 和 Setter（你也可以使用 Lombok 简化）
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getTime() { return time; }
    public int getMaxParticipants() { return maxParticipants; }
    public Long getCreatedBy() { return createdBy; }
    public long getCurrentParticipants() { return currentParticipants; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setTime(LocalDateTime time) { this.time = time; }
    public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    public void setCurrentParticipants(long currentParticipants) { this.currentParticipants = currentParticipants; }
}
