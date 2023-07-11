package com.example.taskManagmentSystem.tms.models;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CreateTask {
    /**
     * title of the task.
     */
    private String title;

    /**
     * description of the task.
     */
    private String description;

    /**
     * due date of the task.
     */
    private Timestamp dueDate;
}
