### Autor: Remigiusz Bartczak

Projekt **wypożyczalni samochodów** zaimplementowany w języku **Java**, mający na celu praktyczne porównanie zastosowania poszczególnych baz danych.

Projekt jest realizowany jako **wielomodułowy projekt Maven** (wymagana Java wersji 25). W każdym module znajduje się plik **docker-compose.yml**, umożliwiający szybkie uruchomienie środowiska bazodanowego poleceniem: `docker-compose up` (Wymagane zainstalowane i uruchomione środowisko **Docker Desktop**).

Następnie, aby przetestować działanie, należy skompilować projekt za pomocą polecenia `mvn clean install` i uruchomić klasę **Main** w wybranym module, np.: pl.lodz.p.carrental.postgresql.Main.

#### Zaimplementowane bazy danych:

1. Relacyjne:
    * PostgreSQL
    * TODO: MySQL
2. Nierelacyjne:
    * MongoDB
    * Redis (MongoDB + Redis)
    * TODO: Cassandra, Kafka

**Uwaga!** Jest to tylko projekt poglądowy. Nie jest idealny, ale można wykorzystać go jako wzorzec do innych projektów.