package com.example.taskManagmentSystem.notificationService.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenService {

    /**
     * protected default contructor.
     */
    protected TokenService() {

    }

    /**
     *
     */
    private static final int DURATION_SECONDS = 40;

    /**
     * get token from authentication service endpoint.
     * 
     * @return token string.
     */
    public static String getToken() {
        ObjectMapper mapper = new ObjectMapper();
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(DURATION_SECONDS))
                .build();
        String json = new StringBuilder()
                .append("{")
                .append("\"emailOrUserName\":\"" + System.getenv("ROBOT_USER") + "\",")
                .append("\"password\":\""
                        + System.getenv("ROBOT_PASSWORD") + "\"")
                .append("}").toString();
        System.out.println(json);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .uri(
                            URI.create(
                                    System.getenv("AUTH_SERVICE_URL")
                                            + "/authentication/api/v1/sign-in"))
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = httpClient.send(
                    request, BodyHandlers.ofString());
            Map<String, String> map = mapper.readValue(
                    response.body(), new TypeReference<Map<String, String>>() {
                    });
            return map.get("token");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "";
        }
    }
}
