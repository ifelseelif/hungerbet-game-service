package com.hungerbet.hungerbetgame.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateRandomRequest {
    private UUID id;
    private String name;
    private String description;
    private Date dateStart;
    private UUID gameId;
    private boolean isHappened;
}
