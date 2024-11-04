package com.ivan.pokedex.application.trainer.favorites.set;

import java.util.Set;

public record SetTrainerFavoritesCommand(Integer trainerId, Set<Integer>favorites) {
}
