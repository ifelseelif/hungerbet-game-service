package com.hungerbet.hungerbetgame;

import com.hungerbet.hungerbetgame.models.CreateRandomRequest;
import com.hungerbet.hungerbetgame.models.domain.Event;
import com.hungerbet.hungerbetgame.models.domain.EventBody;
import com.hungerbet.hungerbetgame.models.domain.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/random-events")
@CrossOrigin(origins = "*")
public class RandomEventController {
    @Autowired
    private EventService eventService;

    @PostMapping
    public void addEvent(@RequestBody CreateRandomRequest eventRequest) {
        EventBody eventBody = EventBody.CreatePlannedEvent(eventRequest.getId());
        Event event = new Event(eventRequest.getGameId(), EventType.random, eventBody);
        eventService.SendEvent(event);
    }
}
