package com.ivan.pokedex.infrastructure.repository.mongo;

import com.ivan.pokedex.domain.Pokemon;
import com.ivan.pokedex.domain.PokemonRepository;
import com.ivan.pokedex.domain.PokemonType;
import com.ivan.pokedex.domain.SearchPokemonCriteria;
import com.ivan.pokedex.infrastructure.repository.mongo.model.PokemonEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PokemonRepositoryMongoAdapterTest {

    private static final PokemonType TYPE = PokemonType.FIRE;
    private static final String NAME = "name";

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private PokemonMongoRepository pokemonMongoRepository;

    @InjectMocks
    private PokemonRepositoryMongoAdapter adapter;

    @Test
    void return_all_pokemon_given_empty_criteria() {
        final PokemonEntity pokemonEntity = mockPokemonEntity();
        final List<PokemonEntity> repositoryResponse = List.of(pokemonEntity);
        final SearchPokemonCriteria criteria = new SearchPokemonCriteria(Optional.empty(), Optional.empty());
        final Query query = new Query();
        when(mongoTemplate.find(eq(query), eq(PokemonEntity.class))).thenReturn(repositoryResponse);

        final List<Pokemon> response = adapter.search(criteria);

        assertThat(response)
            .asList().hasSize(repositoryResponse.size())
            .element(0)
            .isEqualTo(new Pokemon(pokemonEntity.number(), pokemonEntity.name(),
                PokemonType.valueOf(pokemonEntity.type()), pokemonEntity.combatPoints(), pokemonEntity.healthPoints()));

        verify(mongoTemplate).find(eq(query), eq(PokemonEntity.class));
        verifyNoMoreInteractions(mongoTemplate);
    }

    @Test
    void return_all_pokemon_given_criteria() {
        final PokemonEntity pokemonEntity = mockPokemonEntity();
        final List<PokemonEntity> repositoryResponse = List.of(pokemonEntity);
        final SearchPokemonCriteria criteria = new SearchPokemonCriteria(Optional.of(TYPE), Optional.of(NAME));
        final Query query = new Query();
        query.addCriteria(Criteria.where("type").is(TYPE.toString()));
        query.addCriteria(Criteria.where("name").regex(".*" + NAME + ".*"));

        when(mongoTemplate.find(eq(query), eq(PokemonEntity.class))).thenReturn(repositoryResponse);

        final List<Pokemon> response = adapter.search(criteria);

        assertThat(response)
            .asList().hasSize(repositoryResponse.size())
            .element(0)
            .isEqualTo(new Pokemon(pokemonEntity.number(), pokemonEntity.name(),
                    PokemonType.valueOf(pokemonEntity.type()), pokemonEntity.combatPoints(), pokemonEntity.healthPoints()));

        verify(mongoTemplate).find(eq(query), eq(PokemonEntity.class));
        verifyNoMoreInteractions(mongoTemplate);
    }

    @Test
    void return_empty_given_pokemon_number_when_repository_return_empty() {

        when(pokemonMongoRepository.findById(1)).thenReturn(Optional.empty());

        final Optional<Pokemon> response = adapter.get(1);

        assertThat(response)
            .isEmpty();

        verify(pokemonMongoRepository).findById(1);
        verifyNoInteractions(mongoTemplate);
    }

    @Test
    void return_pokemon_given_pokemon_number() {
        final PokemonEntity pokemonEntity = mockPokemonEntity();
        when(pokemonMongoRepository.findById(1)).thenReturn(Optional.of(pokemonEntity));

        final Optional<Pokemon> response = adapter.get(1);

        assertThat(response)
            .isPresent()
            .get()
                .isEqualTo(new Pokemon(pokemonEntity.number(), pokemonEntity.name(),
                    PokemonType.valueOf(pokemonEntity.type()), pokemonEntity.combatPoints(), pokemonEntity.healthPoints()));

        verify(pokemonMongoRepository).findById(1);
        verifyNoInteractions(mongoTemplate);
    }

    private PokemonEntity mockPokemonEntity(){
        return new PokemonEntity(1, "first_pokemon", "FIRE", 123.56, 285.0);
    }
}
