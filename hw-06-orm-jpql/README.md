# Создать приложение хранящее информацию о книгах в библиотеке

#### Цель:
Использовать возможности Spring JDBC и spring-boot-starter-jdbc для подключения к реляционным базам данных

#### Результат:
Приложение с хранением данных в реляционной БД, которое в дальнейшем будем развивать

#### Описание/Пошаговая инструкция выполнения домашнего задания:
Это домашнее задание выполняется НЕ на основе предыдущего.

- [x] Использовать Spring JDBC и реляционную базу (H2 или настоящую реляционную БД)
- [x] Предусмотреть таблицы авторов, книг и жанров.
- [x] Предполагается отношение многие-к-одному (у книги один автор и жанр)
- [x] Интерфейс выполняется на Spring Shell (CRUD книги обязателен, операции с авторами и жанрами - как будет удобно).
- [x] Скрипт создания таблиц и скрипт заполнения данными должны автоматически запускаться с помощью spring-boot-starter-jdbc или подключить миграции (Flyway/Liquibase)
- [x] Написать тесты для всех методов DAO (@JdbcTest) и сервиса работы с книгами (с моком DAO).

#### Рекомендации к выполнению работы:
- Использовать NamedParametersJdbcTemplate
- НЕ делать AbstractDao. 
- НЕ делать наследования в тестах 
- Это домашнее задание является основой для следующих
- Опциональное усложнение - отношения многие-ко-многим (у книги может быть много авторов и/или жанров)