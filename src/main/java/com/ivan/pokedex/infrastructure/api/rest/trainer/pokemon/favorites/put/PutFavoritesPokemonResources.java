package com.ivan.pokedex.infrastructure.api.rest.trainer.pokemon.favorites.put;

import com.ivan.pokedex.application.trainer.favorites.set.SetTrainerFavoritesCommand;
import com.ivan.pokedex.application.trainer.favorites.set.SetTrainerFavoritesPokemon;
import com.ivan.pokedex.domain.exception.TrainerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class PutFavoritesPokemonResources {
    private SetTrainerFavoritesPokemon setTrainerFavoritesPokemon;

    public PutFavoritesPokemonResources(
        final SetTrainerFavoritesPokemon setTrainerFavoritesPokemon
    ) {
        this.setTrainerFavoritesPokemon = setTrainerFavoritesPokemon;
    }

    @PutMapping("/trainer/{trainerId}/pokemon/favorites")
    public void set(@PathVariable final Integer trainerId, @RequestBody final SetTrainerFavoritesDTO request) {
        setTrainerFavoritesPokemon.accept(
            new SetTrainerFavoritesCommand(trainerId, request.favorites()));
    }

    @ExceptionHandler(TrainerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleTrainerNotFoundException() {
    }
}
