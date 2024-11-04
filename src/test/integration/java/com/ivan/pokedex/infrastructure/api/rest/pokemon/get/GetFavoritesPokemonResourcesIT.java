package com.ivan.pokedex.infrastructure.api.rest.pokemon.get;


import com.ivan.pokedex.application.trainer.favorites.get.GetTrainerFavoritesPokemon;
import com.ivan.pokedex.domain.exception.TrainerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class GetFavoritesPokemonResourcesIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetTrainerFavoritesPokemon getTrainerFavoritesPokemon;

    @Test
    void return_not_found_when_get_pokemon_favorites_throw_trainer_not_found_exception() throws Exception {
        when(getTrainerFavoritesPokemon.apply(1))
            .thenThrow(new TrainerNotFoundException());

        mockMvc.perform(get("/trainer/1/pokemon/favorites")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

}
