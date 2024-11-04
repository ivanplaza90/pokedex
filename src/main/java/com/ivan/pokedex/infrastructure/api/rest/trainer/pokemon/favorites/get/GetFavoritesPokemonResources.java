package com.ivan.pokedex.infrastructure.api.rest.trainer.pokemon.favorites.get;

import com.ivan.pokedex.application.trainer.favorites.get.GetTrainerFavoritesPokemon;
import com.ivan.pokedex.domain.exception.TrainerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GetFavoritesPokemonResources {
    private GetTrainerFavoritesPokemon getTrainerFavoritesPokemon;

    public GetFavoritesPokemonResources(
        final GetTrainerFavoritesPokemon getTrainerFavoritesPokemon
    ) {
        this.getTrainerFavoritesPokemon = getTrainerFavoritesPokemon;
    }

    @GetMapping("/trainer/{trainerId}/pokemon/favorites")
    public List<PokemonDTO> get(@PathVariable final Integer trainerId) {
        return getTrainerFavoritesPokemon.apply(trainerId)
            .stream()
            .map(pokemon -> new PokemonDTO(pokemon.number(), pokemon.name(), pokemon.type()))
            .collect(Collectors.toList());
    }

    @ExceptionHandler(TrainerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleTrainerNotFoundException() {
    }
}
