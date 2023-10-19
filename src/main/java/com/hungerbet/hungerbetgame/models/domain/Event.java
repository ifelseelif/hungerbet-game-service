package com.hungerbet.hungerbetgame.models.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Event {
    private UUID gameId;
    private EventType happenedEventType;
    private Date happenedTime;
    private EventBody body;

    public Event(UUID gameId, EventType happenedEventType, EventBody body) {
        this.gameId = gameId;
        this.body = body;
        this.happenedTime = new Date();
        this.happenedEventType = happenedEventType;
    }
}
