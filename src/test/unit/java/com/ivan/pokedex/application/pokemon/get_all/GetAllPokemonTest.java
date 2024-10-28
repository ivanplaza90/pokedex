package com.ivan.pokedex.application.pokemon.get_all;

import com.ivan.pokedex.application.domain.Pokemon;
import com.ivan.pokedex.application.domain.PokemonRepository;
import com.ivan.pokedex.application.domain.PokemonType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAllPokemonTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private GetAllPokemon getAllPokemon;

    @Test
    void return_repository_response() {
        final Pokemon pokemon = mockPokemon();
        final List<Pokemon> repositoryResponse = List.of(pokemon);
        when(pokemonRepository.findAll()).thenReturn(repositoryResponse);

        final List<PokemonView> response = getAllPokemon.handle();

        assertThat(response)
                .asList().hasSize(repositoryResponse.size())
                .element(0)
                .isEqualTo(new PokemonView(pokemon.number(), pokemon.name(), pokemon.type().toString()));

        verify(pokemonRepository).findAll();
        verifyNoMoreInteractions(pokemonRepository);
    }

    private Pokemon mockPokemon(){
        return new Pokemon(1, "first_pokemon", PokemonType.FIRE);
    }
}
