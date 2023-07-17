package com.example.taskManagmentSystem.notificationService.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import com.example.taskManagmentSystem.notificationService.models.Tasks;
import com.example.taskManagmentSystem.notificationService.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NotificationService {

    /**
    * duration in seconds.
    */
    private static final int DURATION_SECONDS = 40;
    
    private String bearerToken;

    public NotificationService(final String token){
        this.bearerToken= "Bearer " + token;
    }

    /**
     * HttpClient object.
     */
    private HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(DURATION_SECONDS))
            .build();
    
   /**
    * ObjectMaper object.
    */
    private ObjectMapper mapper = new ObjectMapper();

    
    public List<Tasks> getListOfTasks() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET().header("Authorization", this.bearerToken)
                    .uri(URI.create(
                            System.getenv("TMS_SERVICE_URL") + "/tms/api/v1/admin/tasks"))
                    .build();
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
            return mapper.readValue(response.body(),
                    new TypeReference<List<Tasks>>() {
                    });
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Tasks getTask(final int id) {
            try {
                    HttpRequest request = HttpRequest.newBuilder()
                                    .GET().header("Authorization", this.bearerToken)
                                    .uri(URI.create(
                                                    System.getenv("TMS_SERVICE_URL") + "/tms/api/v1/admin/tasks/" + id))
                                    .build();
                    HttpResponse<String> response = httpClient.send(request,
                                    HttpResponse.BodyHandlers.ofString());
                    return mapper.readValue(response.body(),
                                    new TypeReference<Tasks>() {
                                    });
            } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    return null;
            }
    }

    public User getUser(final String id) {
            try {
                    HttpRequest request = HttpRequest.newBuilder()
                                    .GET().header("Authorization", this.bearerToken)
                                    .uri(URI.create(
                                                    System.getenv("AUTH_SERVICE_URL") + "/authentication/api/v1/users/" + id))
                                    .build();
                    HttpResponse<String> response = httpClient.send(request,
                                    HttpResponse.BodyHandlers.ofString());
                    return mapper.readValue(response.body(),
                                    new TypeReference<User>() {
                                    });
            } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    return null;
            }
    }

}
