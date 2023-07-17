package com.example.taskManagmentSystem.tms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.taskManagmentSystem.tms.entities.Task;
import com.example.taskManagmentSystem.tms.services.TaskService;

import io.swagger.v3.oas.annotations.Hidden;
@RestController
@RequestMapping("tms/api/v1/admin/tasks")
@Hidden
public class AdminTaskController {
    /**
     * Autowired task service.
     */
    @Autowired
    private TaskService taskService;

    /**
     * @param id     unique id.
     * @param userId user unique id.
     * @return TaskModel object.
     */
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable final int id,
            @RequestAttribute final String userId, @RequestAttribute final String role) {
        if (role.equals("ADMIN")) {
            return taskService.getTask(id);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "UnAuthorized User");
    }

    /**
     * @param skip   numbers of records to be skip.
     * @param limit  numbers of records to be fetched.
     * @param userId user unique id.
     * @return List of Tasks.
     */
    @GetMapping("")
    public List<Task> getAllTasks(
            @RequestParam(defaultValue = "0") final int skip,
            @RequestParam(defaultValue = "10") final int limit,
            @RequestAttribute final String userId, @RequestAttribute final String role) {
        if (role.equals("ADMIN")) {
            return taskService.getListOfTasks(skip, limit);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "UnAuthorized User");
    }
    
}
