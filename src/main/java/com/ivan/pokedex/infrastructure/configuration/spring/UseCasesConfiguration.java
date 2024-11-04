package com.ivan.pokedex.infrastructure.configuration.spring;

import com.ivan.pokedex.application.pokemon.get.GetPokemon;
import com.ivan.pokedex.application.trainer.favorites.get.GetTrainerFavoritesPokemon;
import com.ivan.pokedex.domain.PokemonRepository;
import com.ivan.pokedex.application.pokemon.search.SearchPokemon;
import com.ivan.pokedex.domain.TrainerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfiguration {
    @Bean
    public SearchPokemon searchPokemon(final PokemonRepository repository) {
        return new SearchPokemon(repository);
    }

    @Bean
    public GetPokemon getPokemon(final PokemonRepository repository) {
        return new GetPokemon(repository);
    }

    @Bean
    public GetTrainerFavoritesPokemon getTrainerFavoritesPokemon(
        final TrainerRepository trainerRepository,
        final PokemonRepository pokemonRepository
    ){
        return new GetTrainerFavoritesPokemon(trainerRepository, pokemonRepository);
    }
}
