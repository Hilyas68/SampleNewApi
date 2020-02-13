package com.example.NewsAPI.repository;

import com.example.NewsAPI.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Optional<Author> findByEmail(String email);
    Boolean existsByEmail(String email);
}
