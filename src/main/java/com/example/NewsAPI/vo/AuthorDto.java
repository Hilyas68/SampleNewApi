package com.example.NewsAPI.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDto {
    private long id;
    private String name;
    private String email;
    private String bio;

    public AuthorDto(long id, String name, String email, String bio) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bio = bio;
    }
}
