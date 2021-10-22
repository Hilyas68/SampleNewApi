package com.example.NewsAPI.cucumber.glue;

import com.example.NewsAPI.model.Article;
import com.example.NewsAPI.model.Author;
import com.example.NewsAPI.repository.ArticleRepository;
import com.example.NewsAPI.repository.AuthorRepository;
import com.example.NewsAPI.security.JwtAuthService;
import com.example.NewsAPI.vo.ArticleDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class ArticleSteps {

    private final TestRestTemplate testRestTemplate;
    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;
    private final ObjectMapper objectMapper;
    private final JwtAuthService jwtAuthService;

    private List<Article> expectedArticle;
    private List<Article> actualArticle;
    private ResponseEntity<String> response;
    private MultiValueMap<String, String> headers;
    private String email;

    @Before
    public void setup() {
        expectedArticle = new ArrayList<>();
        actualArticle = new ArrayList<>();
        articleRepository.deleteAll();
        email = "h@gmail.com";
        if (!authorRepository.existsByEmail(email))
            authorRepository.save(new Author("hassan", email, "Profession...", "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG"));

        headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + jwtAuthService.generateToken(email));
    }

    @Given("^the following articles$")
    public void givenTheFollowingArticles(final List<Article> articles) {
        System.out.println(articles.get(0).getTitle());
        expectedArticle.addAll(articles);
        articleRepository.saveAll(articles);
    }

    @Given("^Unauthorized user$")
    public void givenUnAuthorizedUser() {
        System.out.println("Unauthorized User attempt....");
    }

    @When("^the user requests all the articles with token")
    public void whenTheUserRequestsAllTheArticlesWithToken() throws JSONException, JsonProcessingException {

        response = testRestTemplate.exchange("/article/all", HttpMethod.GET,
                new HttpEntity<>(headers), String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject resp = new JSONObject(response.getBody());
            actualArticle.addAll(Arrays.asList(objectMapper.readValue(
                    resp.getJSONArray("data").toString(), Article[].class)));
        }
    }

    @When("^the user requests all the articles without token")
    public void whenTheUserRequestsAllTheArticlesWithoutToken() throws IOException {
        response = testRestTemplate.getForEntity("/article/all", String.class);
    }

    @When("^a user post a new article (.*) with headline (.*) and content (.*)$")
    public void whenAUserPostANewArticle(final String title, final String headline, final String content) {
        ArticleDto req = new ArticleDto(title, headline, content, email);
        Article expected = new Article(title, headline, content);
        expectedArticle.add(expected);
        HttpEntity<ArticleDto> request = new HttpEntity<>(req, headers);
        response = testRestTemplate.exchange("/article/submit", HttpMethod.POST, request, String.class);
    }

    @Then("^forbidden status response is returned")
    public void thenForbiddenStatusResponseIsReturned() {
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }


    @Then("^all the articles are returned")
    public void thenAllTheArticlesAreReturned() {
        validateArticles();
    }

    @Then("^it is saved in the database$")
    public void thenItsInSavedInTheDatabase() {
        actualArticle.addAll((Collection<? extends Article>) articleRepository.findAll());
        validateArticles();
    }

    @And("^it has an id$")
    public void andHasAnId() {
        Assertions.assertNotNull(actualArticle.get(0).getId());
    }

    private void validateArticles() {
        Assertions.assertEquals(expectedArticle.size(), actualArticle.size());
        IntStream.range(0, actualArticle.size())
                .forEach(index -> validateArticle(expectedArticle.get(index), actualArticle.get(index)));
    }

    private void validateArticle(Article expected, Article actual) {
        Assertions.assertEquals(expected.getTitle(), actual.getTitle());
        Assertions.assertEquals(expected.getContent(), actual.getContent());
        Assertions.assertEquals(expected.getHeadline(), actual.getHeadline());
    }

}
