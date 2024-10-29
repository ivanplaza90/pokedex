package com.ivan.pokedex.infrastructure.configuration.spring;

import com.ivan.pokedex.infrastructure.repository.mongo.PokemonMongoRepository;
import com.ivan.pokedex.infrastructure.repository.mongo.PokemonRepositoryMongoAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfrastructureMongoConfiguration {
    @Bean
    public PokemonRepositoryMongoAdapter pokemonRepositoryMongoAdapter(
        final PokemonMongoRepository pokemonMongoRepository
    ) {
        return new PokemonRepositoryMongoAdapter(pokemonMongoRepository);
    }
}
