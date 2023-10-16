package com.hungerbet.hungerbetgame.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse {
    private UUID id;
    private String status;
    private List<PlayerResponse> players;
}
