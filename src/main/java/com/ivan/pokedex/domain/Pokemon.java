package com.ivan.pokedex.domain;

public record Pokemon(
    int number,
    String name,
    PokemonType type,
    double combatPoints,
    double healthPoints
) {
}
