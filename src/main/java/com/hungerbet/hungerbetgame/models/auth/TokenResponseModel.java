package com.hungerbet.hungerbetgame.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseModel {
    private String token;
    private UserResponseModel user;
}
