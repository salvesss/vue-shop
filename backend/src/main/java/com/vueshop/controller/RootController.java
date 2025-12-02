package com.vueshop.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public Map<String, Object> root() {
        return Map.of(
                "message", "Vue Shop backend is running",
                "itemsEndpoint", "/items",
                "favoritesEndpoint", "/favorites",
                "swagger", "Not enabled; use REST client");
    }
}


