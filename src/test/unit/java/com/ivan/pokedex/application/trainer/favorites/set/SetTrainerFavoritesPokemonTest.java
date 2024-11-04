package com.ivan.pokedex.application.trainer.favorites.set;

import com.ivan.pokedex.domain.Trainer;
import com.ivan.pokedex.domain.TrainerRepository;
import com.ivan.pokedex.domain.exception.TrainerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SetTrainerFavoritesPokemonTest {

    private static final SetTrainerFavoritesCommand COMMAND = new SetTrainerFavoritesCommand(1, Set.of());
    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private SetTrainerFavoritesPokemon setTrainerFavoritesPokemon;

    @Test
    void throw_trainer_not_found_exception_when_trainer_repository_returns_empty() {
        when(trainerRepository.get(COMMAND.trainerId()))
                .thenReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> setTrainerFavoritesPokemon.accept(COMMAND));

        assertThat(error)
            .isInstanceOf(TrainerNotFoundException.class);
    }

    @Test
    void update_favorites_when_trainer_repository_returns_a_trainer() {
        when(trainerRepository.get(COMMAND.trainerId()))
                .thenReturn(Optional.of(new Trainer(1, Set.of(1,2,3))));

        setTrainerFavoritesPokemon.accept(COMMAND);

        verify(trainerRepository).get(COMMAND.trainerId());
        verify(trainerRepository).save(new Trainer(COMMAND.trainerId(), COMMAND.favorites()));

        verifyNoMoreInteractions(trainerRepository);
    }
}
