package com.hungerbet.hungerbetgame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungerbet.hungerbetgame.models.CreateRandomRequest;
import com.hungerbet.hungerbetgame.models.domain.Event;
import com.hungerbet.hungerbetgame.models.domain.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/random-events")
@CrossOrigin(origins = "*")
public class RandomEventController {
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private EventService eventService;

    @PostMapping
    public void addEvent(@RequestBody CreateRandomRequest eventRequest) {
        try {
            String body = mapper.writeValueAsString(eventRequest);
            Event event = new Event(eventRequest.getGameId(), EventType.RANDOM_EVENT, body);
            eventService.SendEvent(event);
        } catch (JsonProcessingException ignored) {

        }
    }
}
