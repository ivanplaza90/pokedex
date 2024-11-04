package com.ivan.pokedex.domain;

import java.util.Set;

public record Trainer(Integer trainerId, Set<Integer> favorites) {
}
