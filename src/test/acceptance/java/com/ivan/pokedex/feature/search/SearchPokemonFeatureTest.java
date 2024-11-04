package com.ivan.pokedex.feature.search;

import com.ivan.pokedex.infrastructure.repository.mongo.PokemonMongoRepository;
import com.ivan.pokedex.infrastructure.repository.mongo.model.PokemonEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class SearchPokemonFeatureTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PokemonMongoRepository pokemonMongoRepository;

    @AfterEach
    void clean() {
        pokemonMongoRepository.deleteAll();
    }
    @Test
    void given_not_criteria_and_no_pokemon_exists_when_i_get_pokemon_i_receive_empty_list() throws Exception {
        mockMvc.perform(get("/pokemon")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void given_not_criteria_and_pokemon_stored_when_i_get_all_pokemon_i_received_it() throws Exception {
        final PokemonEntity pokemonEntity = new PokemonEntity(1, "first_pokemon", "FIRE", 100.0, 250.0);
        pokemonMongoRepository.insert(pokemonEntity);

        mockMvc.perform(get("/pokemon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].number").value(pokemonEntity.number()))
                .andExpect(jsonPath("$[0].name").value(pokemonEntity.name()))
                .andExpect(jsonPath("$[0].type").value(pokemonEntity.type()));
    }

    @Test
    void given_type_criteria_and_pokemon_stored_when_i_get_all_pokemon_i_received_the_pokemon() throws Exception {
        final PokemonEntity firstPokemon = new PokemonEntity(1, "first_pokemon", "FIRE", 100.0, 250.0);
        final PokemonEntity secondPokemon = new PokemonEntity(2, "second_pokemon", "WATER", 150.0, 200.0);
        pokemonMongoRepository.insert(List.of(firstPokemon, secondPokemon));

        mockMvc.perform(get("/pokemon?type=WATER")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].number").value(secondPokemon.number()))
                .andExpect(jsonPath("$[0].name").value(secondPokemon.name()))
                .andExpect(jsonPath("$[0].type").value(secondPokemon.type()));
    }

    @Test
    void given_name_criteria_and_pokemon_stored_when_i_get_all_pokemon_i_received_the_pokemon() throws Exception {
        final PokemonEntity firstPokemon = new PokemonEntity(1, "first_pokemon", "FIRE", 100.0, 250.0);
        final PokemonEntity secondPokemon = new PokemonEntity(2, "second_pokemon", "WATER", 150.0, 200.0);
        pokemonMongoRepository.insert(List.of(firstPokemon, secondPokemon));

        mockMvc.perform(get("/pokemon?name=second")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].number").value(secondPokemon.number()))
                .andExpect(jsonPath("$[0].name").value(secondPokemon.name()))
                .andExpect(jsonPath("$[0].type").value(secondPokemon.type()));
    }
}
