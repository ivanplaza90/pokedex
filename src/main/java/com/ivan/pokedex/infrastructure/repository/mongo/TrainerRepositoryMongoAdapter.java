package com.ivan.pokedex.infrastructure.repository.mongo;

import com.ivan.pokedex.domain.Trainer;
import com.ivan.pokedex.domain.TrainerRepository;

import java.util.Optional;

public class TrainerRepositoryMongoAdapter implements TrainerRepository {

    private final TrainerMongoRepository trainerMongoRepository;

    public TrainerRepositoryMongoAdapter(final TrainerMongoRepository trainerMongoRepository) {
        this.trainerMongoRepository = trainerMongoRepository;
    }

    @Override
    public Optional<Trainer> get(final Integer trainerId) {
        return trainerMongoRepository.findById(trainerId)
            .map(trainerEntity -> new Trainer(trainerEntity.trainerId(), trainerEntity.favorites()));
    }
}
