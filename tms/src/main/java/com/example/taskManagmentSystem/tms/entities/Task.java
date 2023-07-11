package com.example.taskManagmentSystem.tms.entities;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tasks")
public class Task {
    /**
     * uniquie id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * title of the task.
     */
    @Column(name = "title")
    private String title;

    /**
     * description of the task.
     */
    @Column(name = "description")
    private String description;

    /**
     * status of the task.
     */
    @Column(name = "status")
    private String status;

    /**
     * user_id of the task.
     */
    @Column(name = "user_id")
    private String user_id;

    /**
     * due date of the task.
     */
    @Column(name="due_date")
    private Timestamp dueDate;

    /**
     * creation timestamp of the task.
     */
    @Column(name = "created_at")
    private Timestamp createdAt;
    
}
