package com.ivan.pokedex.domain;

import java.util.List;

public interface PokemonRepository {
    List<Pokemon> search(SearchPokemonCriteria criteria);
}
