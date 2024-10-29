package com.ivan.pokedex.feature.search;

import com.ivan.pokedex.infrastructure.repository.mongo.PokemonMongoRepository;
import com.ivan.pokedex.infrastructure.repository.mongo.model.PokemonEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class GetPokemonFeature {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PokemonMongoRepository pokemonMongoRepository;

    @BeforeEach
    void clean() {
        pokemonMongoRepository.deleteAll();
    }
    @Test
    void given_a_pokemon_number_and_no_pokemon_exists_when_i_get_pokemon_i_receive_not_found_response() throws Exception {
        pokemonMongoRepository.deleteAll();

        mockMvc.perform(get("/pokemon/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void given_a_pokemon_number_and_pokemon_stored_when_i_get_pokemon_i_received_the_pokemon() throws Exception {
        final PokemonEntity firstPokemon = new PokemonEntity(1, "first_pokemon", "FIRE");
        final PokemonEntity secondPokemon = new PokemonEntity(2, "second_pokemon", "WATER");
        pokemonMongoRepository.insert(List.of(firstPokemon, secondPokemon));

        mockMvc.perform(get("/pokemon/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.number").value(secondPokemon.number()))
                .andExpect(jsonPath("$.name").value(secondPokemon.name()))
                .andExpect(jsonPath("$.type").value(secondPokemon.type()));
    }
}
