# Sklep narciarski
Sklep internetowy przeznaczony do sprzedaży sprzętu narciarskiego.

## Frontend
#### Angular CLI: 11.2.3, Node: 16.2.0
#### Url: http://localhost:4200

## Backend
#### Tomcat: 9.0.30, Java: 13.0.1, MySql 5.7.32
#### Url: http://localhost:8080

## Połączenie z bazą danych
1. Zmienić nazwę pliku `/backend/src/main/resources/application.properties.example` na `/backend/src/main/resources/application.properties`

2. Zmienić ustawienia bazy:
  - spring.datasource.url = jdbc:mysql://localhost:3306/`nazwa_bazy`
  - spring.datasource.username=`nazwa_uzytkownika`
  - spring.datasource.password=`haslo`
