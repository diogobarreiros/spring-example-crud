package com.spring.example.crud.controllers;

import com.spring.example.crud.domain.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static com.spring.example.crud.utils.JsonUtils.toJson;
import static com.spring.example.crud.utils.Random.randomUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureDataJpa
@AutoConfigureMockMvc
public class SessionsTests {

    @Autowired
    private MockMvc api;
    
    @Autowired
    UserRepository repository;

    @Test
    @DisplayName("Must generate the token when the password is correct.")
    public void should_sigh_in_with_correct_password() throws Exception {
        
        var user = randomUser();

        var email = user.getEmail();
        var password = user.getPassword();

        repository.save(user);

        var body = """
            {
                "email": "%s",
                "password": "%s"
            }
        """;

        var request = post("/api/sessions")
            .content(String.format(body, email, password))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

        api.perform(request)
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("It should not generate the token when the password is incorrect.")
    public void dont_should_sigh_in_with_wrong_password() throws Exception {

        var user = repository.save(randomUser());
        
        var body = toJson(Map.of(
            "email", user.getEmail(),
            "password", "passwordIncorrect"
        ));

        var request = post("/api/sessions")
            .content(body)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

        api.perform(request)
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("It must not accept requests without the token in the header when routes are protected.")
    public void dont_should_accept_requests_without_token_on_header_when_authorized_route() throws Exception {
        
        var request = get("/api/users");

        api.perform(request)
            .andDo(print())
            .andExpect(status().isForbidden());
    }
}
