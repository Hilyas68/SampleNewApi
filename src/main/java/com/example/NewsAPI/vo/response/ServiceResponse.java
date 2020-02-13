package com.example.NewsAPI.vo.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponse {
    private String statusMessage;
    private int responseCode;
    private Object data;

    public ServiceResponse(int responseCode) {
        this.responseCode = responseCode;
    }
}
