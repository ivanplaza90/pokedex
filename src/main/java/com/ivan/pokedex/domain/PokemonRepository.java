package com.ivan.pokedex.domain;

import java.util.List;
import java.util.Optional;

public interface PokemonRepository {
    List<Pokemon> search(SearchPokemonCriteria criteria);
    Optional<Pokemon> get(int pokemonNumber);
}
