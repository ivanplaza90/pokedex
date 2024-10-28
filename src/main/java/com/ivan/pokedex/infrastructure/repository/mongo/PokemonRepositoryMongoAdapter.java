package com.ivan.pokedex.infrastructure.repository.mongo;

import com.ivan.pokedex.application.domain.Pokemon;
import com.ivan.pokedex.application.domain.PokemonRepository;
import com.ivan.pokedex.application.domain.PokemonType;

import java.util.List;
import java.util.stream.Collectors;

public class PokemonRepositoryMongoAdapter implements PokemonRepository {

    private final PokemonMongoRepository pokemonMongoRepository;

    public PokemonRepositoryMongoAdapter(PokemonMongoRepository pokemonMongoRepository) {
        this.pokemonMongoRepository = pokemonMongoRepository;
    }

    @Override
    public List<Pokemon> findAll() {
        return pokemonMongoRepository.findAll()
            .stream()
            .map(entity -> new Pokemon(entity.number(), entity.name(), PokemonType.valueOf(entity.type())))
            .collect(Collectors.toList());
    }
}
