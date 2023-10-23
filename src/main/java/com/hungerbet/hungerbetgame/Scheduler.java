package com.hungerbet.hungerbetgame;

import com.hungerbet.hungerbetgame.models.GameResponse;
import com.hungerbet.hungerbetgame.models.PlayerResponse;
import com.hungerbet.hungerbetgame.models.auth.AuthRequest;
import com.hungerbet.hungerbetgame.models.auth.TokenResponseModel;
import com.hungerbet.hungerbetgame.models.domain.Event;
import com.hungerbet.hungerbetgame.models.domain.EventBody;
import com.hungerbet.hungerbetgame.models.domain.EventType;
import com.hungerbet.hungerbetgame.models.domain.PlayerState;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

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

        GameResponse gameResponse = gameResponses.stream().filter(game -> game.getStatus().equals("ongoing")).findFirst().orElse(null);
        PlayerResponse playerResponse = gameResponse.getPlayers().values().stream().flatMap(item -> item.stream()).collect(Collectors.toList()).stream().filter(player -> !player.getState().equals("dead")).findFirst().orElse(null);

        List<EventType> eventTypes = List.of(EventType.supply, EventType.other, EventType.player_killed, EventType.player_injured);
        Random random = new Random();
        EventType eventType = eventTypes.get(random.nextInt(eventTypes.size()));

        if (playerResponse == null) {
            System.out.println("Nothing to send");
            return;
        }

        switch (eventType) {
            case supply -> {
                int itemRnd = new Random().nextInt(Names.items.size());
                String itemName = Names.items.get(itemRnd);
                EventBody eventBody = EventBody.CreateSupplyEvent(playerResponse.getId(), itemName);
                Event event = new Event(gameResponse.getId(), EventType.supply, eventBody);
                eventService.SendEvent(event);
            }
            case player_killed -> {
                EventBody eventBody = EventBody.CreatePlayerEvent(playerResponse.getId(), PlayerState.dead);
                Event event = new Event(gameResponse.getId(), EventType.player_killed, eventBody);
                eventService.SendEvent(event);
            }
            case other -> {
                EventBody eventBody = EventBody.CreateOtherEvent(playerResponse.getId(), "достиг рога изобилия");
                Event event = new Event(gameResponse.getId(), EventType.other, eventBody);
                eventService.SendEvent(event);
            }
            case player_injured -> {
                List<PlayerState> states = List.of(PlayerState.moderate_injury, PlayerState.severe_injury, PlayerState.slight_injury);
                EventBody eventBody = EventBody.CreatePlayerEvent(playerResponse.getId(), states.get(random.nextInt(states.size())));
                Event event = new Event(gameResponse.getId(), EventType.player_injured, eventBody);
                eventService.SendEvent(event);
            }
        }

        System.out.println("SMTH SENDED");
    }
}
