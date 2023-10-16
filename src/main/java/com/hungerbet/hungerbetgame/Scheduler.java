package com.hungerbet.hungerbetgame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungerbet.hungerbetgame.models.GameResponse;
import com.hungerbet.hungerbetgame.models.PlayerResponse;
import com.hungerbet.hungerbetgame.models.auth.AuthRequest;
import com.hungerbet.hungerbetgame.models.auth.TokenResponseModel;
import com.hungerbet.hungerbetgame.models.domain.Event;
import com.hungerbet.hungerbetgame.models.domain.EventType;
import com.hungerbet.hungerbetgame.models.domain.PlayerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@EnableScheduling
public class Scheduler {

    @Value("${main-api}")
    private String apiPath;

    @Autowired
    private EventService eventService;

    @Scheduled(cron = "1 * * * * *")
    public void runPlannedEvents() {
        System.out.println("START SEND");
        RestTemplate template = new RestTemplate();

        AuthRequest authRequest = new AuthRequest("service", "service");
        String token = template.postForObject(apiPath + "/auth/login", authRequest, TokenResponseModel.class).getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        List<GameResponse> gameResponses = template.exchange(apiPath + "/games", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<GameResponse>>() {
        }).getBody();

        GameResponse gameResponse = gameResponses.stream().filter(game -> game.getStatus().equals("ONGOING")).findFirst().orElse(null);
        PlayerResponse playerResponse = gameResponse.getPlayers().stream().filter(player -> !player.getState().equals("DEAD")).findFirst().orElse(null);

        if (playerResponse != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String body = mapper.writeValueAsString(new PlayerEvent(playerResponse));
                Event event = new Event(gameResponse.getId(), EventType.KILLED_EVENT, body);
                eventService.SendEvent(event);
            } catch (JsonProcessingException ignored) {
            }
        }
    }
}
