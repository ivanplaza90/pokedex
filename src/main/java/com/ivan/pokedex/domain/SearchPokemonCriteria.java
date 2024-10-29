package com.ivan.pokedex.domain;

import java.util.Optional;

public record SearchPokemonCriteria(Optional<PokemonType> type, Optional<String> name) {
}
