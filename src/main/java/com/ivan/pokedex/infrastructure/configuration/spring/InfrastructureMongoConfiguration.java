package com.ivan.pokedex.infrastructure.configuration.spring;

import com.ivan.pokedex.infrastructure.repository.mongo.PokemonMongoRepository;
import com.ivan.pokedex.infrastructure.repository.mongo.PokemonRepositoryMongoAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class InfrastructureMongoConfiguration {
    @Bean
    public PokemonRepositoryMongoAdapter pokemonRepositoryMongoAdapter(
            final MongoTemplate mongoTemplate
            ) {
        return new PokemonRepositoryMongoAdapter(mongoTemplate);
    }
}
