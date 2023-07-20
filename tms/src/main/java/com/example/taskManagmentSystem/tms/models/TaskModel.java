package com.example.taskManagmentSystem.tms.models;

import java.sql.Timestamp;

import com.example.taskManagmentSystem.tms.entities.Task;

import lombok.Data;

@Data
public class TaskModel {
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

    public TaskModel(){
        
    }

    /**
     * @param task task object.
     * @return TaskModel object.
     */
    public TaskModel(final Task task) {
        id = task.getId();
        title = task.getTitle();
        description = task.getDescription();
        status = task.getStatus();
        dueDate = task.getDueDate();
    }
}
