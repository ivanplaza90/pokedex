package com.ivan.pokedex.application.pokemon.search;

import com.ivan.pokedex.domain.PokemonRepository;
import com.ivan.pokedex.domain.PokemonType;
import com.ivan.pokedex.domain.SearchPokemonCriteria;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SearchPokemon implements Function<SearchPokemonQuery, List<PokemonView>> {

    private final PokemonRepository repository;

    public SearchPokemon(final PokemonRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PokemonView> apply(final SearchPokemonQuery searchPokemonQuery) {
        final SearchPokemonCriteria criteria = mapCriteria(searchPokemonQuery);
        return repository.search(criteria)
            .stream()
            .map(pokemon -> new PokemonView(pokemon.number(), pokemon.name(), pokemon.type().toString()))
            .collect(Collectors.toList());
    }

    private  SearchPokemonCriteria mapCriteria(final SearchPokemonQuery searchPokemonQuery) {
        return new SearchPokemonCriteria(
            searchPokemonQuery.type().map(PokemonType::valueOf),
            searchPokemonQuery.name());
    }
}
