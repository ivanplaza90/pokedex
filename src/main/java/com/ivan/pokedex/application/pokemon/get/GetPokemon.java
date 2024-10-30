package com.ivan.pokedex.application.pokemon.get;

import com.ivan.pokedex.domain.Pokemon;
import com.ivan.pokedex.domain.PokemonRepository;

import java.util.Optional;
import java.util.function.Function;

public class GetPokemon implements Function<GetPokemonQuery, Optional<PokemonView>> {

    private final PokemonRepository repository;

    public GetPokemon(final PokemonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<PokemonView> apply(final GetPokemonQuery getPokemonQuery) {
        return repository.get(getPokemonQuery.pokemonNumber())
            .map(pokemon -> mapPokemon(pokemon));
    }

    private static PokemonView mapPokemon(Pokemon pokemon) {
        return new PokemonView(pokemon.number(), pokemon.name(), pokemon.type().toString(), pokemon.combatPoints(),
            pokemon.healthPoints());
    }
}
