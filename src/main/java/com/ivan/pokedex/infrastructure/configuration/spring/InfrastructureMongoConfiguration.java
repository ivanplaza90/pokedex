package com.ivan.pokedex.infrastructure.configuration.spring;

import com.ivan.pokedex.infrastructure.repository.mongo.PokemonMongoRepository;
import com.ivan.pokedex.infrastructure.repository.mongo.PokemonRepositoryMongoAdapter;
import com.ivan.pokedex.infrastructure.repository.mongo.TrainerMongoRepository;
import com.ivan.pokedex.infrastructure.repository.mongo.TrainerRepositoryMongoAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class InfrastructureMongoConfiguration {
    @Bean
    public PokemonRepositoryMongoAdapter pokemonRepositoryMongoAdapter(
        final MongoTemplate mongoTemplate,
        final PokemonMongoRepository pokemonMongoRepository
    ) {
        return new PokemonRepositoryMongoAdapter(mongoTemplate, pokemonMongoRepository);
    }

    @Bean
    public TrainerRepositoryMongoAdapter trainerRepositoryMongoAdapter(final TrainerMongoRepository trainerMongoRepository) {
        return new TrainerRepositoryMongoAdapter(trainerMongoRepository);
    }
}
