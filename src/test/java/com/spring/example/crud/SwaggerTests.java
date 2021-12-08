package com.spring.example.crud;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SwaggerTests {

    @Autowired
    private MockMvc api;

    @Test
    @DisplayName("Must display documentation | swagger ui")
    public void should_show_swagger_docs_ui() throws Exception {

        var request = get("/documentation/swagger-ui/index.html");

        api.perform(request)
            .andDo(print())
            .andExpect(status().isOk());
    }
}