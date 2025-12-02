# Листинг кода серверной части приложения "Vue Shop"

## Содержание

1. [Конфигурация проекта (pom.xml)](#1-конфигурация-проекта-pomxml)
2. [Главный класс приложения](#2-главный-класс-приложения)
3. [Модели данных](#3-модели-данных)
   - 3.1. [Класс Item](#31-класс-item)
   - 3.2. [Класс Favorite](#32-класс-favorite)
4. [Репозитории](#4-репозитории)
   - 4.1. [ItemRepository](#41-itemrepository)
   - 4.2. [FavoriteRepository](#42-favoriterepository)
5. [Сервисы](#5-сервисы)
   - 5.1. [ItemService](#51-itemservice)
6. [Контроллеры](#6-контроллеры)
   - 6.1. [ItemController](#61-itemcontroller)
   - 6.2. [FavoriteController](#62-favoritecontroller)
   - 6.3. [RootController](#63-rootcontroller)
7. [Конфигурация](#7-конфигурация)
   - 7.1. [CorsConfig](#71-corsconfig)
   - 7.2. [DataInitializer](#72-datainitializer)
8. [Файл конфигурации приложения](#8-файл-конфигурации-приложения)

---

## 1. Конфигурация проекта (pom.xml)

Файл `pom.xml` определяет зависимости проекта и настройки сборки Maven.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
        <relativePath/>
    </parent>

    <groupId>com.vueshop</groupId>
    <artifactId>vue-shop-backend</artifactId>
    <version>1.0.0</version>
    <name>Vue Shop Backend</name>
    <description>Backend API for Vue Shop</description>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## 2. Главный класс приложения

Класс `VueShopApplication` является точкой входа в приложение Spring Boot.

**Файл:** `src/main/java/com/vueshop/VueShopApplication.java`

```java
package com.vueshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VueShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(VueShopApplication.class, args);
    }
}
```

---

## 3. Модели данных

### 3.1. Класс Item

Класс `Item` представляет сущность товара в базе данных. Использует JPA аннотации для маппинга на таблицу `items`.

**Файл:** `src/main/java/com/vueshop/model/Item.java`

```java
package com.vueshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String imageUrl;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer price;

    public Item() {
    }

    public Item(String title, String imageUrl, Integer price) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
```

### 3.2. Класс Favorite

Класс `Favorite` представляет сущность избранного товара. Связывает товар через поле `parentId`.

**Файл:** `src/main/java/com/vueshop/model/Favorite.java`

```java
package com.vueshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "favorites")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "parent_id")
    private Long parentId;

    public Favorite() {
    }

    public Favorite(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
```

---

## 4. Репозитории

### 4.1. ItemRepository

Интерфейс репозитория для работы с товарами. Наследует стандартные методы CRUD от `JpaRepository`.

**Файл:** `src/main/java/com/vueshop/repository/ItemRepository.java`

```java
package com.vueshop.repository;

import com.vueshop.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
```

### 4.2. FavoriteRepository

Интерфейс репозитория для работы с избранным. Содержит дополнительный метод поиска по `parentId`.

**Файл:** `src/main/java/com/vueshop/repository/FavoriteRepository.java`

```java
package com.vueshop.repository;

import com.vueshop.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByParentId(Long parentId);
}
```

---

## 5. Сервисы

### 5.1. ItemService

Сервисный класс, содержащий бизнес-логику работы с товарами: фильтрацию и сортировку.

**Файл:** `src/main/java/com/vueshop/service/ItemService.java`

```java
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

        final String finalSearchTitle = searchTitle;

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
```

---

## 6. Контроллеры

### 6.1. ItemController

REST контроллер для обработки HTTP-запросов, связанных с товарами.

**Файл:** `src/main/java/com/vueshop/controller/ItemController.java`

```java
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
```

### 6.2. FavoriteController

REST контроллер для обработки HTTP-запросов, связанных с избранным.

**Файл:** `src/main/java/com/vueshop/controller/FavoriteController.java`

```java
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
```

### 6.3. RootController

Контроллер для корневого пути приложения, возвращает информацию о доступных endpoints.

**Файл:** `src/main/java/com/vueshop/controller/RootController.java`

```java
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
```

---

## 7. Конфигурация

### 7.1. CorsConfig

Класс конфигурации для настройки CORS (Cross-Origin Resource Sharing), позволяющий фронтенду обращаться к API.

**Файл:** `src/main/java/com/vueshop/config/CorsConfig.java`

```java
package com.vueshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
```

### 7.2. DataInitializer

Класс для инициализации начальных данных в базе данных при первом запуске приложения.

**Файл:** `src/main/java/com/vueshop/config/DataInitializer.java`

```java
package com.vueshop.config;

import com.vueshop.model.Item;
import com.vueshop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void run(String... args) {
        if (itemRepository.count() == 0) {
            // Initialize with sample bicycle data
            itemRepository.save(new Item("Велосипед AIST Rocky 2.0 Disc 27.5 красный (2023)", "/velo/velo1.jpg", 1051));
            itemRepository.save(new Item("Велосипед AIST Slide 2.0 Disc 29 (2025)", "/velo/velo2.jpg", 1377));
            itemRepository.save(new Item("Велосипед AIST Rosy 1.0 Disc 27.5 (2025)", "/velo/velo3.jpg", 888));
            itemRepository.save(new Item("Велосипед Totem W860 MD 27.5 (2025) белый", "/velo/velo4.jpg", 890));
            itemRepository.save(new Item("Велосипед Marin DSX 2 (2023)", "/velo/velo5.jpg", 3915));
            itemRepository.save(new Item("Велосипед ATOM Tundra PRO MatteConcrete", "/velo/velo6.jpg", 4250));
            itemRepository.save(new Item("Велосипед Titan Racing Valerian Sport Midnight Shine(2024)", "/velo/velo7.jpg", 3200));
            itemRepository.save(new Item("Велосипед Merida eSpeeder 200 SteelBlue/Silver/Black", "/velo/velo8.jpg", 9480));
            itemRepository.save(new Item("Велосипед Atom Nitro (2022)", "/velo/velo9.jpg", 1265));
            itemRepository.save(new Item("Велосипед Forward Iris 26 1.0 (2021)", "/velo/velo10.jpg", 690));
        }
    }
}
```

---

## 8. Файл конфигурации приложения

Файл `application.properties` содержит настройки подключения к базе данных и конфигурацию JPA.

**Файл:** `src/main/resources/application.properties`

```properties
# Server Configuration
server.port=8080

# PostgreSQL Database Configuration
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/vueshop}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:postgres}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:postgres}
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

# Connection Pool Configuration
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
```

---

## Заключение

Представленный листинг кода демонстрирует реализацию серверной части интернет-магазина "Vue Shop" на основе Spring Boot. Приложение использует многослойную архитектуру с четким разделением ответственности:

- **Модели** (Item, Favorite) - определяют структуру данных
- **Репозитории** - обеспечивают доступ к данным через Spring Data JPA
- **Сервисы** - содержат бизнес-логику приложения
- **Контроллеры** - обрабатывают HTTP-запросы и предоставляют REST API
- **Конфигурация** - настраивает CORS и инициализирует начальные данные

Все компоненты следуют принципам SOLID и обеспечивают модульность, расширяемость и поддерживаемость кода.


