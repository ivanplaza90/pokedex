package com.ivan.pokedex.feature.search;

import com.ivan.pokedex.infrastructure.repository.mongo.PokemonMongoRepository;
import com.ivan.pokedex.infrastructure.repository.mongo.TrainerMongoRepository;
import com.ivan.pokedex.infrastructure.repository.mongo.model.PokemonEntity;
import com.ivan.pokedex.infrastructure.repository.mongo.model.TrainerEntity;
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
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class GetTrainerFavoritesPokemon {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TrainerMongoRepository trainerMongoRepository;

    @Autowired
    private PokemonMongoRepository pokemonMongoRepository;

    @AfterEach
    void clean() {
        trainerMongoRepository.deleteAll();
        pokemonMongoRepository.deleteAll();
    }

    @Test
    void given_trainer_that_is_not_created_when_i_get_pokemon_favorite_list_i_receive_not_found_response() throws Exception {

        mockMvc.perform(get("/trainer/1/pokemon/favorites")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    void given_a_trainer_without_favorites_when_i_get_pokemon_favorite_list_i_receive_empty_list() throws Exception {
        trainerMongoRepository.insert(new TrainerEntity(1, Set.of()));

        mockMvc.perform(get("/trainer/1/pokemon/favorites")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void given_a_trainer_with_favorites_when_i_get_pokemon_favorite_list_i_receive_the_list() throws Exception {
        final PokemonEntity firstPokemon = new PokemonEntity(1, "first_pokemon", "FIRE", 100.0, 250.0);
        final PokemonEntity secondPokemon = new PokemonEntity(2, "second_pokemon", "WATER", 150.0, 200.0);
        trainerMongoRepository.insert(new TrainerEntity(1, Set.of(2)));
        pokemonMongoRepository.insert(List.of(firstPokemon, secondPokemon));

        mockMvc.perform(get("/trainer/1/pokemon/favorites")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].number").value(secondPokemon.number()))
                .andExpect(jsonPath("$[0].name").value(secondPokemon.name()))
                .andExpect(jsonPath("$[0].type").value(secondPokemon.type()));
    }
}
