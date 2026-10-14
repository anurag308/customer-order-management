package com.example.customerorders.controller;

import com.example.customerorders.integration.ExternalPost;
import com.example.customerorders.service.ExternalApiService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external/posts")
public class ExternalApiController {

    private final ExternalApiService service;

    public ExternalApiController(ExternalApiService service) {
        this.service = service;
    }

    @Operation(summary = "Fetch a post from JSONPlaceholder")
    @GetMapping("/{id}")
    public ExternalPost getPost(@PathVariable long id) {
        return service.getPost(id);
    }
}