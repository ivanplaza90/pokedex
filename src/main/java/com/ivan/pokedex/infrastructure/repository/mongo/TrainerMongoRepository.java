package com.ivan.pokedex.infrastructure.repository.mongo;

import com.ivan.pokedex.infrastructure.repository.mongo.model.TrainerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrainerMongoRepository extends MongoRepository<TrainerEntity, Integer> {
}
