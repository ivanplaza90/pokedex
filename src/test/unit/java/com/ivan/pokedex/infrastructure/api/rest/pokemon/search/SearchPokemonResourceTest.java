package com.ivan.pokedex.infrastructure.api.rest.pokemon.search;

import com.ivan.pokedex.application.pokemon.search.SearchPokemon;
import com.ivan.pokedex.application.pokemon.search.PokemonView;
import com.ivan.pokedex.application.pokemon.search.SearchPokemonQuery;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchPokemonResourceTest {

    private static final String TYPE = "FIRE";
    private static final String NAME = "name";
    @Mock
    private SearchPokemon searchPokemon;
    @InjectMocks
    private SearchPokemonResource getAllPokemonResource;

    @Test
    void return_empty_pokemon_list_given_not_criteria_when_use_case_returns_empty_list() {
        final List<PokemonView> useCaseResponse = Collections.emptyList();
        final SearchPokemonQuery query = new SearchPokemonQuery(Optional.empty(), Optional.empty());
        when(searchPokemon.apply(eq(query))).thenReturn(useCaseResponse);

        final List<PokemonDTO> response = getAllPokemonResource.search(null, null);

        assertThat(response)
            .isNotNull()
            .size().isEqualTo(useCaseResponse.size());

        verify(searchPokemon).apply(eq(query));
    }

    @Test
    void return_pokemon_list_given_not_criteria_when_use_case_returns_data() {
        final PokemonView pokemonView = mockPokemonView();
        final SearchPokemonQuery query = new SearchPokemonQuery(Optional.empty(), Optional.empty());
        when(searchPokemon.apply(query))
                .thenReturn(List.of(pokemonView));

        final List<PokemonDTO> response = getAllPokemonResource.search(null, null);

        assertThat(response)
                .isNotNull()
                .contains(new PokemonDTO(pokemonView.number(), pokemonView.name(), pokemonView.type()));

        verify(searchPokemon).apply(eq(query));
    }

    @Test
    void return_pokemon_list_given_type_criteria_when_use_case_returns_data() {
        final PokemonView pokemonView = mockPokemonView();
        final SearchPokemonQuery query = new SearchPokemonQuery(Optional.of(TYPE), Optional.empty());

        when(searchPokemon.apply(query))
                .thenReturn(List.of(pokemonView));

        final List<PokemonDTO> response = getAllPokemonResource.search(TYPE, null);

        assertThat(response)
                .isNotNull()
                .contains(new PokemonDTO(pokemonView.number(), pokemonView.name(), pokemonView.type()));

        verify(searchPokemon).apply(eq(query));
    }

    @Test
    void return_pokemon_list_given_name_criteria_when_use_case_returns_data() {
        final PokemonView pokemonView = mockPokemonView();
        final SearchPokemonQuery query = new SearchPokemonQuery(Optional.empty(), Optional.of(NAME));

        when(searchPokemon.apply(query))
                .thenReturn(List.of(pokemonView));

        final List<PokemonDTO> response = getAllPokemonResource.search(null, NAME);

        assertThat(response)
                .isNotNull()
                .contains(new PokemonDTO(pokemonView.number(), pokemonView.name(), pokemonView.type()));

        verify(searchPokemon).apply(eq(query));
    }

    @Test
    void return_pokemon_list_given_all_criteria_when_use_case_returns_data() {
        final PokemonView pokemonView = mockPokemonView();
        final SearchPokemonQuery query = new SearchPokemonQuery(Optional.of(TYPE), Optional.of(NAME));

        when(searchPokemon.apply(query))
                .thenReturn(List.of(pokemonView));

        final List<PokemonDTO> response = getAllPokemonResource.search(TYPE, NAME);

        assertThat(response)
                .isNotNull()
                .contains(new PokemonDTO(pokemonView.number(), pokemonView.name(), pokemonView.type()));

        verify(searchPokemon).apply(eq(query));
    }

    private PokemonView mockPokemonView(){
        return new PokemonView(1, "first_pokemon", "any_type");
    }
}
