package com.example.taskManagmentSystem.tms;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.taskManagmentSystem.tms.entities.Task;
import com.example.taskManagmentSystem.tms.repositories.TaskRepo;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WebAppConfiguration
public class AdminTaskControllerTest {
    @Autowired
    private MockAuthFilter mockAuthFilter;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private TaskRepo taskRepo;

    @Autowired
    private MockMvc mockMvc;

    private Task task;

    @BeforeEach
    void setup() throws ServletException{
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
        this.task = newTask;
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(mockAuthFilter).build();
    }
    @Test
    void getTaskById() throws URISyntaxException, Exception{
        when(taskRepo.findById(task.getId())).thenReturn(Optional.of(task));
        mockMvc.perform(MockMvcRequestBuilders.get(
            new URI("/tms/api/v1/admin/tasks/"+task.getId())).requestAttr("role", "ADMIN")).andExpect(
                MockMvcResultMatchers.status().isOk());
    }
    @Test
    void getTaskByInvalidId() throws URISyntaxException, Exception{
        when(taskRepo.findById(task.getId())).thenReturn(Optional.of(task));
        mockMvc.perform(MockMvcRequestBuilders.get(
            new URI("/tms/api/v1/admin/tasks/"+3)).requestAttr("role", "ADMIN")).andExpect(
                MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    void getListOfTasks() throws URISyntaxException, Exception{
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        when(taskRepo.findAll(0,10)).thenReturn(taskList);
        mockMvc.perform(MockMvcRequestBuilders.get(
            new URI("/tms/api/v1/admin/tasks")).requestAttr("role", "ADMIN")).andExpect(
                MockMvcResultMatchers.status().isOk());
    }
}
