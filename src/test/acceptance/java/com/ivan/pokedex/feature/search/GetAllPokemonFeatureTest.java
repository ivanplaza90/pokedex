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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class GetAllPokemonFeatureTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PokemonMongoRepository pokemonMongoRepository;

    @BeforeEach
    void clean() {
        pokemonMongoRepository.deleteAll();
    }
    @Test
    void given_not_pokemon_when_i_get_pokemon_i_receive_empty_list() throws Exception {
        pokemonMongoRepository.deleteAll();

        mockMvc.perform(get("/pokemon")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void given_a_pokemon_stored_when_i_get_all_pokemon_i_received_it() throws Exception {
        final PokemonEntity pokemonEntity = new PokemonEntity(1, "name", "FIRE");
        pokemonMongoRepository.insert(pokemonEntity);

        mockMvc.perform(get("/pokemon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].number").value(pokemonEntity.number()))
                .andExpect(jsonPath("$[0].name").value(pokemonEntity.name()))
                .andExpect(jsonPath("$[0].type").value(pokemonEntity.type()));
    }
}
