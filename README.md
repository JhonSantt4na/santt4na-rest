# Santt4na REST API

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Build Status](https://travis-ci.org/seu-usuario/santt4na-rest.svg?branch=main)](https://travis-ci.org/seu-usuario/santt4na-rest)
[![GitHub issues](https://img.shields.io/github/issues/seu-usuario/santt4na-rest)](https://github.com/seu-usuario/santt4na-rest/issues)

Este projeto é uma API RESTful desenvolvida com o framework Spring Boot, utilizando JPA (Java Persistence API) para persistência de dados, Flyway para migrações de banco de dados e MySQL como banco de dados.

## Descrição

A API oferece operações básicas de CRUD (Create, Read, Update, Delete) para o gerenciamento de informações sobre pessoas. Além disso, ela também possui uma versão de DTO (Data Transfer Object) para compatibilidade entre diferentes versões da API.

### Funcionalidades:
- **GET /person**: Retorna todos os registros de pessoas.
- **GET /person/{id}**: Retorna uma pessoa específica com base no ID.
- **POST /person**: Cria uma nova pessoa.
- **PUT /person**: Atualiza os dados de uma pessoa existente.
- **DELETE /person/{id}**: Exclui uma pessoa específica pelo ID.
- **POST /person/v2**: Cria uma nova pessoa com uma versão de DTO diferente.

## Requisitos

- JDK 21
- MySQL 5.7 ou superior
- Maven 3.6 ou superior
