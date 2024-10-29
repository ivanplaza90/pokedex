package com.ivan.pokedex.application.pokemon.search;

import com.ivan.pokedex.domain.Pokemon;
import com.ivan.pokedex.domain.PokemonRepository;
import com.ivan.pokedex.domain.PokemonType;
import com.ivan.pokedex.domain.SearchPokemonCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchPokemonTest {

    private static final String TYPE = "FIRE";
    private static final String NAME = "name";

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private SearchPokemon searchPokemon;

    @Test
    void return_repository_response_given_empty_criteria_data() {
        final Pokemon pokemon = mockPokemon();
        final List<Pokemon> repositoryResponse = List.of(pokemon);
        final SearchPokemonQuery query = new SearchPokemonQuery(Optional.empty(), Optional.empty());
        final SearchPokemonCriteria criteria = new SearchPokemonCriteria(Optional.empty(), Optional.empty());

        when(pokemonRepository.search(criteria)).thenReturn(repositoryResponse);

        final List<PokemonView> response = searchPokemon.apply(query);

        assertThat(response)
                .asList().hasSize(repositoryResponse.size())
                .element(0)
                .isEqualTo(new PokemonView(pokemon.number(), pokemon.name(), pokemon.type().toString()));

        verify(pokemonRepository).search(criteria);
        verifyNoMoreInteractions(pokemonRepository);
    }

    @Test
    void return_repository_response_given_all_criteria_data() {
        final Pokemon pokemon = mockPokemon();
        final List<Pokemon> repositoryResponse = List.of(pokemon);
        final SearchPokemonQuery query = new SearchPokemonQuery(Optional.of(TYPE), Optional.of(NAME));
        final SearchPokemonCriteria criteria = new SearchPokemonCriteria(Optional.of(PokemonType.valueOf(TYPE)), Optional.of(NAME));

        when(pokemonRepository.search(criteria)).thenReturn(repositoryResponse);

        final List<PokemonView> response = searchPokemon.apply(query);

        assertThat(response)
                .asList().hasSize(repositoryResponse.size())
                .element(0)
                .isEqualTo(new PokemonView(pokemon.number(), pokemon.name(), pokemon.type().toString()));

        verify(pokemonRepository).search(criteria);
        verifyNoMoreInteractions(pokemonRepository);
    }

    private Pokemon mockPokemon(){
        return new Pokemon(1, "first_pokemon", PokemonType.FIRE);
    }
}
