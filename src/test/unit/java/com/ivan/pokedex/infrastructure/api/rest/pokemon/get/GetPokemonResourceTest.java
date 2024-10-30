package com.ivan.pokedex.infrastructure.api.rest.pokemon.get;

import com.ivan.pokedex.application.pokemon.get.GetPokemon;
import com.ivan.pokedex.application.pokemon.get.GetPokemonQuery;
import com.ivan.pokedex.application.pokemon.get.PokemonView;
import com.ivan.pokedex.domain.pokemon.PokemonNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetPokemonResourceTest {

    private static final int POKEMON_NUMBER = 1;
    @Mock
    private GetPokemon getPokemon;
    @InjectMocks
    private GetPokemonResource getPokemonResource;

    @Test
    void throw_pokemon_not_found_exception_when_repository_returns_empty() {
        final GetPokemonQuery query = new GetPokemonQuery(POKEMON_NUMBER);
        when(getPokemon.apply(eq(query))).thenReturn(Optional.empty());

        assertThatException()
            .isThrownBy(() -> getPokemonResource.get(POKEMON_NUMBER))
            .isInstanceOf(PokemonNotFoundException.class);
    }

    @Test
    void return_a_pokemon_when_repository_returns_the_pokemon() {
        final GetPokemonQuery query = new GetPokemonQuery(POKEMON_NUMBER);
        final PokemonView pokemonView = mockPokemonView();
        when(getPokemon.apply(eq(query))).thenReturn(Optional.of(pokemonView));

        final PokemonDTO response = getPokemonResource.get(POKEMON_NUMBER);

        assertThat(response)
            .isNotNull()
            .isEqualTo(new PokemonDTO(pokemonView.number(), pokemonView.name(), pokemonView.type()));
    }

    private PokemonView mockPokemonView(){
        return new PokemonView(1, "first_pokemon", "any_type");
    }
}
