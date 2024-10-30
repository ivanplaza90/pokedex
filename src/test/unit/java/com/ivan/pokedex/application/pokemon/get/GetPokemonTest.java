package com.ivan.pokedex.application.pokemon.get;

import com.ivan.pokedex.domain.Pokemon;
import com.ivan.pokedex.domain.PokemonRepository;
import com.ivan.pokedex.domain.PokemonType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetPokemonTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private GetPokemon getPokemon;

    @Test
    void return_empty_when_repository_returns_empty() {
        when(pokemonRepository.get(1)).thenReturn(Optional.empty());

        final Optional<PokemonView> response = getPokemon.apply(new GetPokemonQuery(1));

        assertThat(response)
            .isEmpty();
    }

    @Test
    void return_pokemon_view_when_repository_returns_a_pokemon() {
        final Pokemon pokemon = mockPokemon();
        final List<Pokemon> repositoryResponse = List.of(pokemon);
        when(pokemonRepository.get(1)).thenReturn(Optional.of(pokemon));

        final Optional<PokemonView> response = getPokemon.apply(new GetPokemonQuery(1));

        assertThat(response)
            .isPresent()
            .get()
            .isEqualTo(new PokemonView(pokemon.number(), pokemon.name(), pokemon.type().toString(), pokemon.combatPoints(), pokemon.healthPoints()));
    }

    private Pokemon mockPokemon(){
        return new Pokemon(1, "first_pokemon", PokemonType.FIRE, 111.2, 234.9);
    }
}
