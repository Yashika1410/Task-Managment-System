package com.example.taskManagmentSystem.tms.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.taskManagmentSystem.tms.entities.Task;
import com.example.taskManagmentSystem.tms.models.CreateTaskModel;
import com.example.taskManagmentSystem.tms.models.TaskModel;
import com.example.taskManagmentSystem.tms.repositories.TaskRepo;

@Service
public class TaskService {
    /**
     * autowired taskrepo object.
     */
    @Autowired
    private TaskRepo taskRepo;

    /**
     * @param taskModel Create Task request model.
     * @param userId unique user id.
     * @return TaskModel object.
     */
    public TaskModel createNewTask(final CreateTaskModel taskModel,
    final String userId) {
        Task task = new Task();
        task.setTitle(taskModel.getTitle());
        task.setDescription(taskModel.getDescription());
        task.setDueDate(taskModel.getDueDate());
        task.setUserId(userId);
        task.setStatus("Created");
        task.setDueDate(taskModel.getDueDate());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        task.setCreatedAt(timestamp);
        return new TaskModel(taskRepo.save(task));
    }

    /**
     * @param id unique id.
     * @param userId unique user id.
     * @return TaskModel object.
     */
    public TaskModel getTaskByUserId(final int id, final String userId) {
        return new TaskModel(
            taskRepo.findById(id, userId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Task Not Found by this id " + id)));
    }

    /**
     * @param id     unique id.
     * @return Task object.
     */
    public Task getTask(final int id) {
        return taskRepo.findById(id).orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Task Not Found by this id " + id));
    }

    /**
     * @param userId unique user id.
     * @param skip skip value.
     * @param limit  limit value.
     * @return TaskModel object.
     */
    public List<TaskModel> getListOfTasksByUserId(final String userId,
    final int skip, final int limit) {
        return taskRepo.findAllByUserId(
                userId, skip, limit).stream()
                .map(task -> new TaskModel(task))
                .collect(Collectors.toList());
    }

    /**
     * @param skip   skip value.
     * @param limit  limit value.
     * @return Task object.
     */
    public List<Task> getListOfTasks(final int skip,
    final int limit) {
        return taskRepo.findAll(skip, limit);
    }

    /**
     * @param userId  unique user id.
     * @param id unique id.
     * @param taskModel TaskModel object.
     * @return TaskModel object.
     */
    public TaskModel updateTask(final String userId,
    final int id, final TaskModel taskModel) {
        Task task = taskRepo.findById(id, userId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Task Not Found by this id " + id));
        task.setTitle(taskModel.getTitle());
        task.setDueDate(taskModel.getDueDate());
        task.setDescription(taskModel.getDescription());
        task.setStatus(taskModel.getStatus());
        return new TaskModel(taskRepo.save(task));
    }
}
