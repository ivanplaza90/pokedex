package com.ivan.pokedex.infrastructure.api.rest.trainer.pokemon.favorites.put;

import com.ivan.pokedex.application.trainer.favorites.set.SetTrainerFavoritesCommand;
import com.ivan.pokedex.application.trainer.favorites.set.SetTrainerFavoritesPokemon;
import com.ivan.pokedex.domain.exception.TrainerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PutFavoritesPokemonTest {

    private static final Integer TRAINER_ID = 1;
    private static final Set<Integer> FAVORITES = Set.of();

    @Mock
    private SetTrainerFavoritesPokemon setTrainerFavoritesPokemon;
    @InjectMocks
    private PutFavoritesPokemonResources putFavoritesPokemonResources;

    @Test
    void throws_trainer_not_found_exception_when_use_case_throws_it() {
        final SetTrainerFavoritesDTO request = new SetTrainerFavoritesDTO(FAVORITES);
        final TrainerNotFoundException trainerNotFoundException = new TrainerNotFoundException();
        doThrow(trainerNotFoundException).when(setTrainerFavoritesPokemon)
                .accept(new SetTrainerFavoritesCommand(TRAINER_ID, FAVORITES));

        final Throwable error = catchThrowable(() -> putFavoritesPokemonResources.set(TRAINER_ID, request));

        assertThat(error)
            .isEqualTo(trainerNotFoundException);
    }

    @Test
    void do_nothing_when_use_case_success() {
        final SetTrainerFavoritesDTO request = new SetTrainerFavoritesDTO(FAVORITES);
        doNothing().when(setTrainerFavoritesPokemon)
            .accept(new SetTrainerFavoritesCommand(TRAINER_ID, FAVORITES));

        putFavoritesPokemonResources.set(TRAINER_ID, request);

        verify(setTrainerFavoritesPokemon).accept(new SetTrainerFavoritesCommand(TRAINER_ID, FAVORITES));
        verifyNoMoreInteractions(setTrainerFavoritesPokemon);
    }

}
