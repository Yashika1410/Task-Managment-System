package com.example.taskManagmentSystem.notificationService.cronjob;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.taskManagmentSystem.notificationService.service.EmailService;
import com.example.taskManagmentSystem.notificationService.service.NotificationService;
import com.example.taskManagmentSystem.notificationService.service.TokenService;

@Component
public class NotificationCronJob {


    @Autowired
    private EmailService emailService;



    @Scheduled(cron = "0 0 */1 * * *")
    public void cronjob(){
        NotificationService notificationService = new NotificationService(
            TokenService.getToken());
        notificationService.getListOfTasks().forEach(task ->{
            if(task.getDueDate().after(new Timestamp(System.currentTimeMillis()))) {
                emailService.sendEmail(notificationService.getUser(task.getUserId()),
                "Your task "+task.getTitle()+" got over due",
                "Alert From Task Managment System");
            }
        });

    }
}
