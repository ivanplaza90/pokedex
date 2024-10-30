package com.ivan.pokedex.infrastructure.api.rest.pokemon.get;

import com.ivan.pokedex.application.pokemon.get.GetPokemon;
import com.ivan.pokedex.application.pokemon.get.GetPokemonQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class GetPokemonResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetPokemon getPokemon;

    @Test
    void return_not_found_when_get_pokemon_throw_pokemon_not_found_exception() throws Exception {
        when(getPokemon.apply(new GetPokemonQuery(1))).thenReturn(Optional.empty());

        mockMvc.perform(get("/pokemon/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

}
