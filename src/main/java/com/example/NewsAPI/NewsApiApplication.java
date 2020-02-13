package com.example.NewsAPI;

import com.example.NewsAPI.model.Author;
import com.example.NewsAPI.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NewsApiApplication {
    @Autowired
    private AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(NewsApiApplication.class, args);
	}

//	@Bean
//    CommandLineRunner runner(){
//        return args -> {
//            authorRepository.save(new Author("hassan","h@gmail.com","Profession...","$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG"));
//        };
    //}
}
