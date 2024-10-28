package com.ivan.pokedex.infrastructure.configuration.spring;

import com.ivan.pokedex.application.domain.PokemonRepository;
import com.ivan.pokedex.application.pokemon.get_all.GetAllPokemon;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfiguration {
    @Bean
    public GetAllPokemon getAllPokemon(final PokemonRepository repository) {
        return new GetAllPokemon(repository);
    }
}
