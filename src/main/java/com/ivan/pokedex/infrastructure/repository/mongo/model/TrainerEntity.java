package com.ivan.pokedex.infrastructure.repository.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection="trainer")
public record TrainerEntity(@Id Integer trainerId, Set<Integer> favorites) {
}
