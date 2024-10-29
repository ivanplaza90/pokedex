package com.ivan.pokedex.infrastructure.repository.mongo;

import com.ivan.pokedex.infrastructure.repository.mongo.model.PokemonEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PokemonMongoRepository extends MongoRepository<PokemonEntity, String> {
}
