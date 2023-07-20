package com.example.taskManagmentSystem.tms.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.taskManagmentSystem.tms.models.CreateTaskModel;
import com.example.taskManagmentSystem.tms.models.TaskModel;
import com.example.taskManagmentSystem.tms.services.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("tms/api/v1/tasks")
public class TaskController {

    /**
     * Autowired task service.
     */
    @Autowired
    private TaskService taskService;

    /**
     * @param id unique id.
     * @param userId user unique id.
     * @return TaskModel object.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Task by id",
    security = @SecurityRequirement(name = "bearerAuth"))
    public TaskModel getTaskById(@PathVariable final int id,
    @RequestAttribute final String userId) {
        return taskService.getTaskByUserId(id, userId);
    }

    /**
     * @param skip numbers of records to be skip.
     * @param limit numbers of records to be fetched.
     * @param userId user unique id.
     * @return List of Tasks.
     */
    @GetMapping("")
    @Operation(summary = "Get List of tasks",
    security = @SecurityRequirement(name = "bearerAuth"))
    public List<TaskModel> getAllTasks(
    @RequestParam(defaultValue = "0") final int skip,
    @RequestParam(defaultValue = "10") final int limit,
    @RequestAttribute final String userId) {
        return taskService.getListOfTasksByUserId(userId, skip, limit);
    }

    /**
     * @param createTaskModel CreateTaskModel object.
     * @param userId user unique id
     * @return Task object.
     */
    @PostMapping("")
    @Operation(summary = "Create new task",
    security = @SecurityRequirement(name = "bearerAuth"))
    public TaskModel createNewTask(
            @Valid @RequestBody final CreateTaskModel createTaskModel,
            @RequestAttribute final String userId) {
        return taskService.createNewTask(createTaskModel, userId);
    }

    /**
     * @param createTaskModel CreateTaskModel object.
     * @param userId user unique id
     * @return Task object.
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Update task",
    security = @SecurityRequirement(name = "bearerAuth"))
    public TaskModel updateTask(
            @Valid @RequestBody final TaskModel taskModel,
            @RequestAttribute final String userId,@PathVariable final int id) {
        return taskService.updateTask(userId,id,taskModel);
    }

}
