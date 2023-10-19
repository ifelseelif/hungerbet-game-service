package com.hungerbet.hungerbetgame.models.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Data
@NoArgsConstructor
public class EventBody {
    @Nullable
    private UUID playerId;
    @Nullable
    private PlayerState playerState;
    @Nullable
    private String text;
    @Nullable
    private UUID plannedEventId;
    @Nullable
    private UUID itemId;

    public static EventBody CreatePlannedEvent(UUID plannedEventId) {
        EventBody eventBody = new EventBody();

        eventBody.plannedEventId = plannedEventId;

        return eventBody;
    }

    public static EventBody CreatePlayerEvent(UUID playerId, PlayerState playerState) {
        EventBody eventBody = new EventBody();

        eventBody.playerId = playerId;
        eventBody.playerState = playerState;

        return eventBody;
    }
}
