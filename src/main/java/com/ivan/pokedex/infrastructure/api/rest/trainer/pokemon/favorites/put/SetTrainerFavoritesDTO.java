package com.ivan.pokedex.infrastructure.api.rest.trainer.pokemon.favorites.put;

import java.util.Set;

public record SetTrainerFavoritesDTO(Set<Integer> favorites) {
}
