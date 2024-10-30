package com.ivan.pokedex.infrastructure.repository.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="pokemon")
public record PokemonEntity(
    @Id int number,
    String name,
    String type,
    double combatPoints,
    double healthPoints
) {
}
