package com.hungerbet.hungerbetgame.models.domain;

import com.hungerbet.hungerbetgame.models.PlayerResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class PlayerEvent {
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private UUID playerId;

    public PlayerEvent(PlayerResponse playerResponse) {
        this.firstName = playerResponse.getFirstName();
        this.lastName = playerResponse.getLastName();
        this.age = playerResponse.getAge();
        this.gender = playerResponse.getGender();
        this.playerId = playerResponse.getId();
    }
}
