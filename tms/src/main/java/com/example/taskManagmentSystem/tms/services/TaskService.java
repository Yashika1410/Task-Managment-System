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
    final int userId) {
        Task task = new Task();
        task.setTitle(taskModel.getTitle());
        task.setDescription(taskModel.getDescription());
        task.setDueDate(taskModel.getDueDate());
        task.setUserId(userId);
        task.setStatus("Created");
        task.setDueDate(taskModel.getDueDate());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        task.setCreatedAt(timestamp);
        return TaskModel.convertTaskToTaskModel(taskRepo.save(task));
    }

    /**
     * @param id unique id.
     * @param userId unique user id.
     * @return TaskModel object.
     */
    public TaskModel getTask(final int id, final int userId) {
        return TaskModel.convertTaskToTaskModel(
            taskRepo.findById(id, userId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Task Not Found by this id " + id)));
    }

    /**
     * @param userId unique user id.
     * @param skip skip value.
     * @param limit  limit value.
     * @return TaskModel object.
     */
    public List<TaskModel> getListOfTasks(final int userId,
    final int skip, final int limit) {
        return taskRepo.findAllByUserId(
                userId, skip, limit).stream()
                .map(task -> TaskModel.convertTaskToTaskModel(task))
                .collect(Collectors.toList());
    }

    /**
     * @param userId  unique user id.
     * @param id unique id.
     * @param taskModel TaskModel object.
     * @return TaskModel object.
     */
    public TaskModel updateTask(final int userId,
    final int id, final TaskModel taskModel) {
        Task task = taskRepo.findById(id, userId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Task Not Found by this id " + id));
        task.setTitle(taskModel.getTitle());
        task.setDueDate(taskModel.getDueDate());
        task.setDescription(taskModel.getDescription());
        task.setStatus(taskModel.getStatus());
        return TaskModel.convertTaskToTaskModel(taskRepo.save(task));
    }
}
