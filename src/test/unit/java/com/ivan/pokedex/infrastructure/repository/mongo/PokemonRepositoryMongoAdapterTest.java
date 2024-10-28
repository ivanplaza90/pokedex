package com.ivan.pokedex.infrastructure.repository.mongo;

import com.ivan.pokedex.application.domain.Pokemon;
import com.ivan.pokedex.application.domain.PokemonType;
import com.ivan.pokedex.infrastructure.repository.mongo.model.PokemonEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PokemonRepositoryMongoAdapterTest {

    @Mock
    private PokemonMongoRepository pokemonMongoRepository;

    @InjectMocks
    private PokemonRepositoryMongoAdapter adapter;

    @Test
    void return_all_pokemon() {
        final PokemonEntity pokemonEntity = mockPokemonEntity();
        final List<PokemonEntity> repositoryResponse = List.of(pokemonEntity);
        when(pokemonMongoRepository.findAll()).thenReturn(repositoryResponse);

        final List<Pokemon> response = adapter.findAll();

        assertThat(response)
                .asList().hasSize(repositoryResponse.size())
                .element(0)
                .isEqualTo(new Pokemon(pokemonEntity.number(), pokemonEntity.name(), PokemonType.valueOf(pokemonEntity.type())));

        verify(pokemonMongoRepository).findAll();
        verifyNoMoreInteractions(pokemonMongoRepository);
    }

    private PokemonEntity mockPokemonEntity(){
        return new PokemonEntity(1, "first_pokemon", "FIRE");
    }
}
