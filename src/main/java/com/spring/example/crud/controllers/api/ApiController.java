package com.spring.example.crud.controllers.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(tags = "Status check", produces = "application/json")
@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping
    public String index() {
        return "API online!";
    }
}