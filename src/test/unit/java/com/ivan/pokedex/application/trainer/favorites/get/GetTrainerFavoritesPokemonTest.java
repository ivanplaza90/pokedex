package com.ivan.pokedex.application.trainer.favorites.get;

import com.ivan.pokedex.domain.*;
import com.ivan.pokedex.domain.exception.TrainerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetTrainerFavoritesPokemonTest {

    private static final int TRAINER_ID = 1;
    public static final int POKEMON_ID = 4;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private PokemonRepository pokemonRepository;
    @InjectMocks
    private GetTrainerFavoritesPokemon getTrainerFavoritesPokemon;

    @Test
    void throws_trainer_not_found_exception_when_trainer_repository_returns_empty_trainer() {

        when(trainerRepository.get(TRAINER_ID)).thenReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> getTrainerFavoritesPokemon.apply(TRAINER_ID));

        assertThat(error)
            .isInstanceOf(TrainerNotFoundException.class);
    }

    @Test
    void return_empty_list_when_trainer_favorites_list_is_empty() {

        when(trainerRepository.get(TRAINER_ID)).thenReturn(Optional.of(mockTrainer(Set.of())));

        final List<PokemonView> response = getTrainerFavoritesPokemon.apply(TRAINER_ID);

        assertThat(response)
            .isNotNull()
            .hasSize(0);
    }

    @Test
    void return_favorite_list_when_trainer_favorites_list_is_not_empty() {

        final Pokemon pokemon = mockPokemon(POKEMON_ID);
        final Trainer trainer = mockTrainer(Set.of(POKEMON_ID));
        when(trainerRepository.get(TRAINER_ID)).thenReturn(Optional.of(trainer));
        when(pokemonRepository.get(4)).thenReturn(Optional.of(pokemon));

        final List<PokemonView> response = getTrainerFavoritesPokemon.apply(TRAINER_ID);

        assertThat(response)
            .isNotNull()
            .hasSize(trainer.favorites().size())
            .element(0)
            .isNotNull()
            .isEqualTo(new PokemonView(pokemon.number(), pokemon.name(), pokemon.type().toString()));
    }

    private Trainer mockTrainer(final Set favorites) {
        return new Trainer(TRAINER_ID, favorites);
    }

    private Pokemon mockPokemon(final Integer pokemonId){
        return new Pokemon(pokemonId, "first_pokemon", PokemonType.FIRE, 111.2, 234.9);
    }
}
