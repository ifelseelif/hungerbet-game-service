package com.hungerbet.hungerbetgame.models.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class RandomEvent {
    private UUID id;
    private String name;
    private String description;
    private Date dateStart;
    private UUID gameId;
    private boolean isHappened;

    public RandomEvent(UUID gameId, String description, String name, Date dateStart) {
        this.id = UUID.randomUUID();
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.dateStart = dateStart;
        this.isHappened = true;
    }
}
