package com.example.NewsAPI.vo.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse extends ServiceResponse {
    private String accessToken;

    public LoginResponse(int responseCode) {
        super(responseCode);
    }
}
