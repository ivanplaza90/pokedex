package com.ivan.pokedex.application.trainer.favorites.set;

import com.ivan.pokedex.domain.Trainer;
import com.ivan.pokedex.domain.TrainerRepository;
import com.ivan.pokedex.domain.exception.TrainerNotFoundException;

import java.util.function.Consumer;

public class SetTrainerFavoritesPokemon implements Consumer<SetTrainerFavoritesCommand> {

    private final TrainerRepository trainerRepository;

    public SetTrainerFavoritesPokemon(final TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public void accept(final SetTrainerFavoritesCommand command) {
        final Trainer updatedTrainer = trainerRepository.get(command.trainerId())
                .map(trainer -> new Trainer(trainer.trainerId(), command.favorites()))
                .orElseThrow(() -> new TrainerNotFoundException());
        trainerRepository.save(updatedTrainer);
    }
}
