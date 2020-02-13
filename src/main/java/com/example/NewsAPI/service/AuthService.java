package com.example.NewsAPI.service;

import com.example.NewsAPI.model.Author;
import com.example.NewsAPI.repository.AuthorRepository;
import com.example.NewsAPI.vo.request.LoginDto;
import com.example.NewsAPI.vo.request.SignUpDto;
import com.example.NewsAPI.vo.response.LoginResponse;
import com.example.NewsAPI.vo.response.ServiceResponse;
import com.example.NewsAPI.security.JwtAuthService;
import com.example.NewsAPI.util.Constant;
import com.example.NewsAPI.vo.AuthorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    JwtAuthService jwtAuthService;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    public LoginResponse authenticate(LoginDto request){
        LoginResponse response = new LoginResponse(Constant.ERROR_CODE);
       try {
           try {
                authenticationManager.authenticate(
                       new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
               );
           }catch (BadCredentialsException ex){
               response.setStatusMessage(Constant.INCORRECT_CREDENTIAL);
               return response;
           }

           String token = jwtAuthService.generateToken(request.getUsername());

           response.setAccessToken(token);
           response.setResponseCode(Constant.SUCCESS_CODE);
           response.setStatusMessage(Constant.LOGIN_SUCCESS);
           return response;

       }catch (Exception ex){
           response.setStatusMessage(Constant.ERROR_PROCESSING);
           return response;
       }
    }

    public ServiceResponse registerUser(SignUpDto signUpRequest){
        ServiceResponse response = new ServiceResponse(Constant.ERROR_CODE);
        try{
            if(authorRepository.existsByEmail(signUpRequest.getEmail())){
                response.setStatusMessage(Constant.EMAIL_EXIST);
                return response;
            }

            Author author = new Author(signUpRequest.getName(),signUpRequest.getEmail(),
                    signUpRequest.getBio(),encoder.encode(signUpRequest.getPassword()));

            authorRepository.save(author);

            response.setResponseCode(Constant.SUCCESS_CODE);
            response.setStatusMessage(Constant.SIGNUP_SUCCESS);
            return response;

        }catch (Exception ex){
            response.setStatusMessage(Constant.ERROR_PROCESSING);
            return response;
        }
    }

    public ServiceResponse getAllAuthors(){
        ServiceResponse response = new ServiceResponse(Constant.ERROR_CODE);
        try{
            Iterable<Author> authorsIterable = authorRepository.findAll();
            List<AuthorDto> authors = new ArrayList<>();
            authorsIterable.forEach(e ->
                    authors.add(new AuthorDto(e.getId(), e.getName(), e.getEmail(), e.getBio()))
            );

            response.setResponseCode(Constant.SUCCESS_CODE);
            response.setData(authors);
            response.setStatusMessage(Constant.OPERATION_SUCCESS);
            return response;

        }catch (Exception ex){
            response.setStatusMessage(Constant.ERROR_PROCESSING);
            return response;
        }
    }
}
