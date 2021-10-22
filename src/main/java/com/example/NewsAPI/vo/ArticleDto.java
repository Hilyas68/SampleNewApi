package com.example.NewsAPI.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ArticleDto {
    private long id;
    @NotBlank
    private String title;
    @NotBlank
    private String headline;
    @NotBlank
    private String content;
    @NotBlank
    private String email;
    private LocalDateTime addedAt;

    public ArticleDto(long id, String title, String headline, String content, LocalDateTime addedAt) {
        this.id = id;
        this.title = title;
        this.headline = headline;
        this.content = content;
        this.addedAt = addedAt;
    }

    public ArticleDto(String title, String headline, String content, String email) {
        this.title = title;
        this.headline = headline;
        this.content = content;
        this.email = email;
    }
}
