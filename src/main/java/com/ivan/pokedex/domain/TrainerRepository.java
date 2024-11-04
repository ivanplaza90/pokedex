package com.ivan.pokedex.domain;

import java.util.Optional;

public interface TrainerRepository {
    Optional<Trainer> get(Integer trainerId);
}
