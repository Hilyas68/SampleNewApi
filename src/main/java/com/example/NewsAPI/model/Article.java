package com.example.NewsAPI.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Article {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @NotBlank
    private String title;
    private String headline;
    @NotBlank
    private String content;
    private LocalDateTime addedAt;
    private LocalDateTime modifiedAt;
    @JoinColumn(name = "authorId", referencedColumnName = "Id")
    @ManyToOne
    private Author authorId;

    public Article(String title, String headline, String content, LocalDateTime addedAt, LocalDateTime modifiedAt, Author authorId) {
        this.title = title;
        this.headline = headline;
        this.content = content;
        this.addedAt = addedAt;
        this.modifiedAt = modifiedAt;
        this.authorId = authorId;
    }

    public Article() {
    }

    public Article(String title, String headline, String content) {
        this.title = title;
        this.headline = headline;
        this.content = content;
    }
}
