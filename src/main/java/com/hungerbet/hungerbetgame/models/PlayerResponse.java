package com.hungerbet.hungerbetgame.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class PlayerResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String state;
}
