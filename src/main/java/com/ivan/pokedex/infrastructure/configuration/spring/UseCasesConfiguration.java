package com.ivan.pokedex.infrastructure.configuration.spring;

import com.ivan.pokedex.application.pokemon.get.GetPokemon;
import com.ivan.pokedex.domain.PokemonRepository;
import com.ivan.pokedex.application.pokemon.search.SearchPokemon;
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
}
