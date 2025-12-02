package com.vueshop.service;

import com.vueshop.model.Item;
import com.vueshop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getItems(String title, String sortBy) {
    // Remove wildcard characters from title if present
    String searchTitle = title;
    if (searchTitle != null && searchTitle.startsWith("*") && searchTitle.endsWith("*")) {
        searchTitle = searchTitle.substring(1, searchTitle.length() - 1);
    }

    final String finalSearchTitle = searchTitle; // <-- финальная переменная

    List<Item> items;
    if (finalSearchTitle == null || finalSearchTitle.isEmpty()) {
        items = itemRepository.findAll();
    } else {
        items = itemRepository.findAll().stream()
                .filter(item -> item.getTitle().toLowerCase().contains(finalSearchTitle.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Apply sorting
    if (sortBy != null) {
        switch (sortBy) {
            case "name":
                items.sort(Comparator.comparing(Item::getTitle));
                break;
            case "price":
                items.sort(Comparator.comparing(Item::getPrice));
                break;
            case "-price":
                items.sort(Comparator.comparing(Item::getPrice).reversed());
                break;
            default:
                items.sort(Comparator.comparing(Item::getTitle));
                break;
        }
    }

    return items;
}
}





