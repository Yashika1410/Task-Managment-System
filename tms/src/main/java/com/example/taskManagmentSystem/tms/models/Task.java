package com.example.taskManagmentSystem.tms.models;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class Task {
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
}
