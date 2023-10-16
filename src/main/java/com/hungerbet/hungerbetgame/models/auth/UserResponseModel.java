package com.hungerbet.hungerbetgame.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseModel {
    private UUID id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
}
