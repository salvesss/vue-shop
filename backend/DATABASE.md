# Работа с базой данных PostgreSQL

## Настройка PostgreSQL

### Вариант 1: Использование Docker (Рекомендуется)

Самый простой способ запустить PostgreSQL - использовать Docker Compose:

```bash
cd backend
docker-compose up -d
```

Это запустит PostgreSQL в контейнере с настройками:

- **Хост:** `localhost`
- **Порт:** `5432`
- **База данных:** `vueshop`
- **Пользователь:** `postgres`
- **Пароль:** `postgres`

Остановить контейнер:

```bash
docker-compose down
```

Остановить и удалить данные:

```bash
docker-compose down -v
```

### Вариант 2: Локальная установка PostgreSQL

1. Установите PostgreSQL на вашу систему:

   - Windows: https://www.postgresql.org/download/windows/
   - macOS: `brew install postgresql@15`
   - Linux: `sudo apt-get install postgresql postgresql-contrib`

2. Создайте базу данных:

   ```bash
   psql -U postgres
   CREATE DATABASE vueshop;
   \q
   ```

3. Обновите `application.properties` с вашими учетными данными:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/vueshop
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

## Конфигурация

Файл `backend/src/main/resources/application.properties` содержит настройки подключения:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/vueshop
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

### Параметры JPA

- `ddl-auto=update` - автоматически создает/обновляет таблицы при запуске
- `show-sql=true` - выводит SQL запросы в консоль (для отладки)

## Где изменить данные

### Способ 1: Через код (начальные данные)

Измените файл `backend/src/main/java/com/vueshop/config/DataInitializer.java`:

```java
itemRepository.save(new Item("Название товара", "/velo/velo1.jpg", 25000));
```

**Важно:** Данные добавляются только если база пустая (`itemRepository.count() == 0`).

### Способ 2: Через SQL клиент

#### Использование psql (командная строка)

```bash
psql -h localhost -U postgres -d vueshop
# Введите пароль: postgres
```

#### Использование pgAdmin

1. Установите pgAdmin: https://www.pgadmin.org/download/
2. Создайте новое подключение:
   - Host: `localhost`
   - Port: `5432`
   - Database: `vueshop`
   - Username: `postgres`
   - Password: `postgres`

#### Использование DBeaver

1. Установите DBeaver: https://dbeaver.io/download/
2. Создайте новое подключение PostgreSQL
3. Введите параметры подключения

### Способ 3: Через REST API

Используйте API endpoints для работы с данными:

- `GET /items` - получить все товары
- `POST /favorites` - добавить в избранное
- `DELETE /favorites/{id}` - удалить из избранного

## Полезные SQL запросы

### Просмотр данных

```sql
-- Все товары
SELECT * FROM items ORDER BY id;

-- Все избранное
SELECT * FROM favorites;

-- Товары с количеством добавлений в избранное
SELECT i.*, COUNT(f.id) as favorite_count
FROM items i
LEFT JOIN favorites f ON i.id = f.parent_id
GROUP BY i.id
ORDER BY favorite_count DESC;
```

### Добавление данных

```sql
-- Добавить новый товар
INSERT INTO items (title, image_url, price)
VALUES ('Новый велосипед', '/velo/velo1.jpg', 30000);

-- Добавить несколько товаров
INSERT INTO items (title, image_url, price) VALUES
('Горный велосипед', '/velo/velo1.jpg', 25000),
('Шоссейный велосипед', '/velo/velo2.jpg', 35000);
```

### Обновление данных

```sql
-- Изменить цену товара
UPDATE items SET price = 28000 WHERE id = 1;

-- Изменить название товара
UPDATE items SET title = 'Новое название' WHERE id = 1;

-- Изменить несколько полей
UPDATE items
SET title = 'Обновленное название', price = 30000
WHERE id = 1;
```

### Удаление данных

```sql
-- Удалить товар
DELETE FROM items WHERE id = 1;

-- Удалить все избранное для товара
DELETE FROM favorites WHERE parent_id = 1;

-- Удалить все данные (осторожно!)
TRUNCATE TABLE items CASCADE;
TRUNCATE TABLE favorites;
```

### Резервное копирование

```bash
# Создать дамп базы данных
pg_dump -h localhost -U postgres -d vueshop > backup.sql

# Восстановить из дампа
psql -h localhost -U postgres -d vueshop < backup.sql
```

## Структура таблиц

### Таблица `items`

| Колонка   | Тип       | Описание        |
| --------- | --------- | --------------- |
| id        | BIGSERIAL | Первичный ключ  |
| title     | VARCHAR   | Название товара |
| image_url | VARCHAR   | URL изображения |
| price     | INTEGER   | Цена            |

### Таблица `favorites`

| Колонка   | Тип       | Описание                 |
| --------- | --------- | ------------------------ |
| id        | BIGSERIAL | Первичный ключ           |
| parent_id | BIGINT    | ID товара (внешний ключ) |

## Миграции базы данных

При использовании `ddl-auto=update`, Hibernate автоматически создает и обновляет схему базы данных.

Для production рекомендуется использовать Flyway или Liquibase для управления миграциями:

### Flyway

1. Добавьте зависимость в `pom.xml`:

```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

2. Создайте миграции в `src/main/resources/db/migration/`

### Liquibase

1. Добавьте зависимость в `pom.xml`:

```xml
<dependency>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-core</artifactId>
</dependency>
```

2. Создайте файлы изменений в `src/main/resources/db/changelog/`

## Устранение проблем

### Ошибка подключения

```
Connection refused
```

**Решение:** Убедитесь, что PostgreSQL запущен:

```bash
# Проверка через Docker
docker ps | grep postgres

# Проверка локального PostgreSQL
sudo systemctl status postgresql  # Linux
brew services list | grep postgres  # macOS
```

### Ошибка аутентификации

```
password authentication failed
```

**Решение:** Проверьте username и password в `application.properties`

### База данных не существует

```
database "vueshop" does not exist
```

**Решение:** Создайте базу данных:

```sql
CREATE DATABASE vueshop;
```

### Порт занят

Если порт 5432 занят, измените порт в `docker-compose.yml`:

```yaml
ports:
  - '5433:5432' # Используйте 5433 вместо 5432
```

И обновите `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/vueshop
```
