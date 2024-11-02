package com.ivan.pokedex.infrastructure.api.rest.trainer.pokemon.favorites.get;

import com.ivan.pokedex.application.trainer.GetTrainerFavoritesPokemon;

import java.util.List;
import java.util.stream.Collectors;

public class GetFavoritesPokemonResources {
    private GetTrainerFavoritesPokemon getTrainerFavoritesPokemon;

    public GetFavoritesPokemonResources(
        final GetTrainerFavoritesPokemon getTrainerFavoritesPokemon
    ) {
        this.getTrainerFavoritesPokemon = getTrainerFavoritesPokemon;
    }

    public List<PokemonDTO> get(final Integer trainerId) {
        return getTrainerFavoritesPokemon.apply(trainerId)
            .stream()
            .map(pokemon -> new PokemonDTO(pokemon.number(), pokemon.name(), pokemon.type()))
            .collect(Collectors.toList());
    }
}
