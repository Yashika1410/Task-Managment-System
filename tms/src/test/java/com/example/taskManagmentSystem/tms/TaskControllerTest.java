package com.example.taskManagmentSystem.tms;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.example.taskManagmentSystem.tms.entities.Task;
import com.example.taskManagmentSystem.tms.models.CreateTaskModel;
import com.example.taskManagmentSystem.tms.models.TaskModel;
import com.example.taskManagmentSystem.tms.repositories.TaskRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WebAppConfiguration
public class TaskControllerTest {
    
    @Autowired
    private MockAuthFilter mockAuthFilter;

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TaskRepo taskRepo;

    @Autowired
    private MockMvc mockMvc;

    private Task task;

    @BeforeEach
    void setup() throws ServletException{
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(mockAuthFilter).build();
        Task newTask = new Task();
        newTask.setStatus("Created");
        newTask.setDescription("test desc");
        newTask.setTitle("test");
        newTask.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 10);
        newTask.setDueDate(new Timestamp(calendar.getTimeInMillis()));
        newTask.setId(2);
        newTask.setUserId("1234567sdfghjk");
        this.task=newTask;
    }
    @Test
    void getTaskById() throws URISyntaxException, Exception{
        when(taskRepo.findByIdAndUserId(task.getId(),task.getUserId())).thenReturn(Optional.of(task));
        mockMvc.perform(MockMvcRequestBuilders.get(
            new URI("/tms/api/v1/tasks/"+task.getId())).requestAttr("userId", task.getUserId())).andExpect(
                MockMvcResultMatchers.status().isOk());
    }
    @Test
    void getTaskByInvalidId() throws URISyntaxException, Exception{
        when(taskRepo.findByIdAndUserId(task.getId(),task.getUserId())).thenReturn(Optional.of(task));
        mockMvc.perform(MockMvcRequestBuilders.get(
            new URI("/tms/api/v1/tasks/"+3)).requestAttr("userId", task.getUserId())).andExpect(
                MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    void getTaskByValidIdInvalidUser() throws URISyntaxException, Exception{ 
        when(taskRepo.findByIdAndUserId(task.getId(),task.getUserId())).thenReturn(Optional.of(task));
        mockMvc.perform(MockMvcRequestBuilders.get(
            new URI("/tms/api/v1/tasks/"+task.getId())).requestAttr("userId", "3456789ghhh")).andExpect(
                MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    void getListOfTasks() throws URISyntaxException, Exception{
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        when(taskRepo.findAllByUserId(task.getUserId(),0,10)).thenReturn(taskList);
        mockMvc.perform(MockMvcRequestBuilders.get(
            new URI("/tms/api/v1/tasks")).requestAttr("userId", task.getUserId())).andExpect(
                MockMvcResultMatchers.status().isOk());
    }
    @Test
    void createNewTask() throws URISyntaxException, Exception{
        CreateTaskModel createTaskModel = new CreateTaskModel(task.getTitle(),
        task.getDescription(), task.getDueDate());
        when(taskRepo.save(any())).thenReturn(task);
        mockMvc.perform(MockMvcRequestBuilders.post(
            new URI("/tms/api/v1/tasks")).requestAttr("userId",
                    task.getUserId()).contentType(
                    MediaType.APPLICATION_JSON).content(
                mapper.writeValueAsString(createTaskModel))).andExpect(
                MockMvcResultMatchers.status().isOk());
    }
    @Test
    void  updateTask() throws JsonProcessingException, URISyntaxException, Exception{
        TaskModel taskModel = new TaskModel(task);
        when(taskRepo.save(any())).thenReturn(task);
        when( taskRepo.findByIdAndUserId(task.getId(),task.getUserId())).thenReturn(Optional.of(task));
        mockMvc.perform(MockMvcRequestBuilders.patch(
            new URI("/tms/api/v1/tasks/"+task.getId())).requestAttr("userId",
                    task.getUserId()).contentType(
                    MediaType.APPLICATION_JSON).content(
                mapper.writeValueAsString(taskModel))).andExpect(
                MockMvcResultMatchers.status().isOk());
    }
    @Test
    void  updateTaskInvalidId() throws JsonProcessingException, URISyntaxException, Exception{
        TaskModel taskModel = new TaskModel(task);
        when(taskRepo.save(any())).thenReturn(task);
        when( taskRepo.findByIdAndUserId(task.getId(),task.getUserId())).thenReturn(Optional.of(task));
        mockMvc.perform(MockMvcRequestBuilders.patch(
            new URI("/tms/api/v1/tasks/"+0)).requestAttr("userId",
                    task.getUserId()).contentType(
                    MediaType.APPLICATION_JSON).content(
                mapper.writeValueAsString(taskModel))).andExpect(
                MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    void  updateTaskValidIdInvalidUser() throws JsonProcessingException, URISyntaxException, Exception{
        TaskModel taskModel = new TaskModel(task);
        when(taskRepo.save(any())).thenReturn(task);
        when( taskRepo.findByIdAndUserId(task.getId(),task.getUserId())).thenReturn(Optional.of(task));
        mockMvc.perform(MockMvcRequestBuilders.patch(
            new URI("/tms/api/v1/tasks/"+task.getId())).requestAttr("userId",
                    "i15555dddf").contentType(
                    MediaType.APPLICATION_JSON).content(
                mapper.writeValueAsString(taskModel))).andExpect(
                MockMvcResultMatchers.status().isNotFound());
    }
}
