package com.example.NewsAPI.controller;

import com.example.NewsAPI.service.ArticleService;
import com.example.NewsAPI.vo.ArticleDto;
import com.example.NewsAPI.vo.request.SignUpDto;
import com.example.NewsAPI.vo.response.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/article/submit")
    public ResponseEntity<ServiceResponse> submitArticle(@RequestBody ArticleDto articleDto){

        ServiceResponse response = articleService.addArticle(articleDto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/article/edit/{id}")
    public ResponseEntity<ServiceResponse> updateArticle(@PathVariable("id") long id,  @RequestBody ArticleDto articleDto){

        ServiceResponse response = articleService.editArticle(id , articleDto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/article/delete/{id}")
    public ResponseEntity<ServiceResponse> deleteArticle(@PathVariable("id") long id,  @RequestBody ArticleDto articleDto){

        ServiceResponse response = articleService.deleteArticle(id, articleDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/article/all")
    public ResponseEntity<ServiceResponse> gelAllArticles(){

        ServiceResponse response = articleService.getAllArtcles();

        return ResponseEntity.ok(response);
    }

//    @GetMapping("/articles/{id}")
//    public ResponseEntity<Article> getArticles(int id){
//
//        Article article = service.findById(id);
//        if(article == null){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>(article, null,  HttpStatus.NO_CONTENT);
//    }
}
