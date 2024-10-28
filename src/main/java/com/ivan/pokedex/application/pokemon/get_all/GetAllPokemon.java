package com.ivan.pokedex.application.pokemon.get_all;

import com.ivan.pokedex.application.domain.PokemonRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllPokemon {

    private final PokemonRepository repository;

    public GetAllPokemon(final PokemonRepository repository) {
        this.repository = repository;
    }

    public List<PokemonView> handle() {
        return repository.findAll()
            .stream()
            .map(pokemon -> new PokemonView(pokemon.number(), pokemon.name(), pokemon.type().toString()))
            .collect(Collectors.toList());
    }
}
