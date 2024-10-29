package com.ivan.pokedex.infrastructure.api.rest.pokemon.search;

import com.ivan.pokedex.application.pokemon.search.SearchPokemon;
import com.ivan.pokedex.application.pokemon.search.SearchPokemonQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
class SearchPokemonResource {

    private final SearchPokemon searchPokemon;

    SearchPokemonResource(final SearchPokemon searchPokemon) {
        this.searchPokemon = searchPokemon;
    }

    @GetMapping("/pokemon")
    public List<PokemonDTO> search(
            @RequestParam(required = false) final String type,
            @RequestParam(required = false) final String name
    ) {
        final SearchPokemonQuery query = new SearchPokemonQuery(Optional.ofNullable(type), Optional.ofNullable(name));

        return searchPokemon.apply(query)
            .stream()
            .map(pokemonView -> new PokemonDTO(pokemonView.number(), pokemonView.name(), pokemonView.type()))
            .collect(Collectors.toList());
    }
}
