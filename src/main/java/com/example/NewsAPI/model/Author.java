package com.example.NewsAPI.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String bio;
    @NotBlank
    private String password;

    public Author(String name, String email, String bio, String password) {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.password = password;
    }

    public Author() {
    }
}
