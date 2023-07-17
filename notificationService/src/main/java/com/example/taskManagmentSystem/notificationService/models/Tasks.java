package com.example.taskManagmentSystem.notificationService.models;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Tasks {

    /**
     * uniquie id.
     */
    private int id;

    /**
     * title of the task.
     */
    private String title;

    /**
     * description of the task.
     */
    private String description;

    /**
     * status of the task.
     */
    private String status;

    /**
     * due date of the task.
     */
    private Timestamp dueDate;

    /**
     * user_id of the task.
     */
    private String userId;

    /**
     * creation timestamp of the task.
     */
    private Timestamp createdAt;
    
}
