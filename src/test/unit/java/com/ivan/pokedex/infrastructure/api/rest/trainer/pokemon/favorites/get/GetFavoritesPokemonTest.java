package com.ivan.pokedex.infrastructure.api.rest.trainer.pokemon.favorites.get;

import com.ivan.pokedex.application.trainer.favorites.get.GetTrainerFavoritesPokemon;
import com.ivan.pokedex.application.trainer.favorites.get.PokemonView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetFavoritesPokemonTest {

    private static final Integer TRAINER_ID = 1;

    @Mock
    private GetTrainerFavoritesPokemon getTrainerFavoritesPokemon;
    @InjectMocks
    private GetFavoritesPokemonResources getFavoritesPokemonResources;

    @Test
    void throws_trainer_not_found_exception_when_use_case_throws_it() {
        final RuntimeException runtimeException = new RuntimeException();
        when(getTrainerFavoritesPokemon.apply(TRAINER_ID)).thenThrow(runtimeException);

        final Throwable error = catchThrowable(() -> getFavoritesPokemonResources.get(TRAINER_ID));

        assertThat(error)
            .isEqualTo(runtimeException);
    }

    @Test
    void return_trainer_favorites_pokemon_when_use_case_returns_a_list() {
        final List<PokemonView> pokemonList = List.of(mockPokemonView());
        when(getTrainerFavoritesPokemon.apply(TRAINER_ID)).thenReturn(pokemonList);

        final List<PokemonDTO> response = getFavoritesPokemonResources.get(TRAINER_ID);

        assertThat(response)
            .isNotNull()
            .hasSize(pokemonList.size())
            .contains(new PokemonDTO(1, "first_pokemon", "any_type"));
    }

    private PokemonView mockPokemonView(){
        return new PokemonView(1, "first_pokemon", "any_type");
    }
}
