package com.example.taskManagmentSystem.tms.models;

import java.sql.Timestamp;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreateTaskModel {
    /**
     * title of the task.
     */
    @NonNull
    private String title;

    /**
     * description of the task.
     */
    @NonNull
    private String description;

    /**
     * due date of the task.
     */
    @NonNull
    private Timestamp dueDate;
}
