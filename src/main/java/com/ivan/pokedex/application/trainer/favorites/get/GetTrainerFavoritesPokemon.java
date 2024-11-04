package com.ivan.pokedex.application.trainer.favorites.get;

import com.ivan.pokedex.domain.Pokemon;
import com.ivan.pokedex.domain.PokemonRepository;
import com.ivan.pokedex.domain.TrainerRepository;
import com.ivan.pokedex.domain.exception.TrainerNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetTrainerFavoritesPokemon implements Function<Integer, List<PokemonView>> {

    private final TrainerRepository trainerRepository;
    private final PokemonRepository pokemonRepository;

    public GetTrainerFavoritesPokemon(
        final TrainerRepository trainerRepository,
        final PokemonRepository pokemonRepository
    ) {
        this.trainerRepository = trainerRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public List<PokemonView> apply(final Integer trainerId) {
        return trainerRepository.get(trainerId)
            .orElseThrow(TrainerNotFoundException::new)
            .favorites()
            .stream()
            .map(pokemonRepository::get)
            .map(Optional::get)
            .map(this::mapPokemon)
            .collect(Collectors.toList());
    }

    private PokemonView mapPokemon(Pokemon pokemon) {
        return new PokemonView(pokemon.number(), pokemon.name(), pokemon.type().toString());
    }
}
