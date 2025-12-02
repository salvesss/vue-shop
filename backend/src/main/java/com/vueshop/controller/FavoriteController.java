package com.vueshop.controller;

import com.vueshop.model.Favorite;
import com.vueshop.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "*")
public class FavoriteController {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @GetMapping
    public ResponseEntity<List<Favorite>> getAllFavorites() {
        List<Favorite> favorites = favoriteRepository.findAll();
        return ResponseEntity.ok(favorites);
    }

    @PostMapping
    public ResponseEntity<Favorite> addFavorite(@RequestBody Map<String, Long> request) {
        Long parentId = request.get("parentId");
        if (parentId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Favorite favorite = new Favorite(parentId);
        Favorite saved = favoriteRepository.save(favorite);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long id) {
        if (!favoriteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        favoriteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}





