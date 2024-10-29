package com.ivan.pokedex.infrastructure.api.rest.pokemon.get_all;

import com.ivan.pokedex.application.pokemon.get_all.GetAllPokemon;
import com.ivan.pokedex.application.pokemon.get_all.PokemonView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllPokemonResourceTest {

    @Mock
    private GetAllPokemon getAllPokemon;
    @InjectMocks
    private GetAllPokemonResource getAllPokemonResource;

    @Test
    void return_empty_pokemon_list_when_use_case_returns_empty_list() {
        final List<PokemonView> useCaseResponse = Collections.emptyList();
        when(getAllPokemon.handle()).thenReturn(useCaseResponse);

        final List<PokemonDTO> response = getAllPokemonResource.getAll();

        assertThat(response)
            .isNotNull()
            .size().isEqualTo(useCaseResponse.size());

        verify(getAllPokemon).handle();
    }

    @Test
    void return_pokemon_list_when_use_case_returns_data() {
        final PokemonView pokemonView = mockPokemonView();
        when(getAllPokemon.handle())
                .thenReturn(List.of(pokemonView));

        final List<PokemonDTO> response = getAllPokemonResource.getAll();

        assertThat(response)
                .isNotNull()
                .contains(new PokemonDTO(pokemonView.number(), pokemonView.name(), pokemonView.type()));

        verify(getAllPokemon).handle();
    }

    private PokemonView mockPokemonView(){
        return new PokemonView(1, "first_pokemon", "any_type");
    }
}
