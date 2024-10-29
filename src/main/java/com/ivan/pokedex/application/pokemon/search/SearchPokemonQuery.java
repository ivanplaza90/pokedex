package com.ivan.pokedex.application.pokemon.search;

import java.util.Optional;

public record SearchPokemonQuery(Optional<String> type, Optional<String> name) {
}
