package com.vueshop.controller;

import com.vueshop.model.Item;
import com.vueshop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@CrossOrigin(origins = "*")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getItems(
            @RequestParam(required = false) String title,
            @RequestParam(required = false, defaultValue = "name") String sortBy) {
        
        List<Item> items = itemService.getItems(title, sortBy);
        return ResponseEntity.ok(items);
    }
}

