package com.ivan.pokedex.infrastructure.api.rest.pokemon.get_all;

import com.ivan.pokedex.application.pokemon.get_all.GetAllPokemon;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
class GetAllPokemonResource {

    private final GetAllPokemon getAllPokemon;

    GetAllPokemonResource(final GetAllPokemon getAllPokemon) {
        this.getAllPokemon = getAllPokemon;
    }

    @GetMapping("/pokemon")
    public List<PokemonDTO> getAll() {
        return getAllPokemon.handle()
            .stream()
            .map(pokemonView -> new PokemonDTO(pokemonView.number(), pokemonView.name(), pokemonView.type()))
            .collect(Collectors.toList());
    }
}
