package com.hungerbet.hungerbetgame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungerbet.hungerbetgame.models.domain.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EventService {

    @Value("${main-api}")
    private String apiPath;

    @Value("${token}")
    private String token;


    public void SendEvent(Event event) {
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Event> entity = new HttpEntity<>(event, headers);
        template.postForEntity(apiPath + "/game-events", entity, String.class);
    }
}
