package com.example.NewsAPI.service;

import com.example.NewsAPI.model.Article;
import com.example.NewsAPI.model.Author;
import com.example.NewsAPI.repository.ArticleRepository;
import com.example.NewsAPI.repository.AuthorRepository;
import com.example.NewsAPI.util.Constant;
import com.example.NewsAPI.vo.ArticleDto;
import com.example.NewsAPI.vo.response.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    AuthorRepository authorRepository;

    public ServiceResponse addArticle(ArticleDto articleDto){
        ServiceResponse response = new ServiceResponse(Constant.ERROR_CODE);
        try{
            if(articleDto.getTitle() != null && articleDto.getContent() != null && articleDto.getEmail() != null){
                Author author = authorRepository.findByEmail(articleDto.getEmail()).orElse(null);

                articleRepository.save(new Article(
                        articleDto.getTitle(), articleDto.getHeadline(), articleDto.getContent(),
                        LocalDateTime.now(), null, author));

                response.setResponseCode(Constant.SUCCESS_CODE);
                response.setStatusMessage(Constant.OPERATION_SUCCESS);
                return response;
            }
            else {
                response.setStatusMessage(Constant.USER_NOT_FOUND);
                return response;
            }
        }catch (Exception ex){
            response.setStatusMessage(ex.getMessage());
            return response;
        }
    }

    public ServiceResponse editArticle(long id, ArticleDto articleDto){
        ServiceResponse response = new ServiceResponse(Constant.ERROR_CODE);
        try{
            Article article = articleRepository.findById(id).orElse(null);

            if(article != null){
                article.setTitle(articleDto.getTitle());
                article.setHeadline(articleDto.getHeadline());
                article.setContent(articleDto.getContent());
                article.setModifiedAt(LocalDateTime.now());
                article.setId(id);

                articleRepository.save(article);
                response.setResponseCode(Constant.SUCCESS_CODE);
                response.setStatusMessage(Constant.OPERATION_SUCCESS);
                return response;
            }else {
                response.setStatusMessage(Constant.NOT_FOUND);
                return response;
            }

        }catch (Exception ex){
            response.setStatusMessage(Constant.ERROR_PROCESSING);
            return response;
        }
    }

    public ServiceResponse getAllArtcles(){
        ServiceResponse response = new ServiceResponse(Constant.ERROR_CODE);
        try{
            Iterable<Article> authorsIterable = articleRepository.findAll();
            List<ArticleDto> articles = new ArrayList<>();
            authorsIterable.forEach(e ->
                    articles.add(new ArticleDto(e.getId(),e.getTitle(), e.getHeadline(), e.getContent(),e.getAddedAt()))
            );

            response.setResponseCode(Constant.SUCCESS_CODE);
            response.setData(articles);
            response.setStatusMessage(Constant.OPERATION_SUCCESS);
            return response;

        }catch (Exception ex){
            response.setStatusMessage(Constant.ERROR_PROCESSING);
            return response;
        }
    }

    public ServiceResponse deleteArticle(long id, ArticleDto articleDto){
        ServiceResponse response = new ServiceResponse(Constant.ERROR_CODE);
        try{
            Author author = authorRepository.findByEmail(articleDto.getEmail()).orElse(null);

            Article article = articleRepository.findById(id).orElse(null);

            if(author != null && article != null) {
                if (author.getId() == article.getAuthorId().getId()) {
                    articleRepository.delete(article);
                    response.setResponseCode(Constant.SUCCESS_CODE);
                    response.setStatusMessage(Constant.OPERATION_SUCCESS);
                    return response;
                } else {
                    response.setStatusMessage(Constant.NOT_PERMITED);
                    return response;
                }
            }else {
                response.setStatusMessage(Constant.NOT_FOUND);
                return response;
            }
        }catch (Exception ex){
            response.setStatusMessage(ex.getMessage());
            return response;
        }
    }
}
