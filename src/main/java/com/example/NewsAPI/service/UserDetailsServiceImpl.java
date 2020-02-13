package com.example.NewsAPI.service;

import com.example.NewsAPI.model.Author;
import com.example.NewsAPI.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthorRepository authorRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Author user =  authorRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User : " + email + " not found"));
        return new User(user.getName(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority("author")));

    }
}
