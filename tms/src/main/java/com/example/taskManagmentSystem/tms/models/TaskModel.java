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

    /**
     * @param task task object.
     * @return TaskModel object.
     */
    public static TaskModel convertTaskToTaskModel(final Task task) {
        TaskModel taskModel = new TaskModel();
        taskModel.setId(task.getId());
        taskModel.setTitle(task.getTitle());
        taskModel.setDescription(task.getDescription());
        taskModel.setStatus(task.getStatus());
        taskModel.setDueDate(task.getDueDate());
        return taskModel;
    }
}
