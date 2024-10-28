package com.ivan.pokedex.application.domain;

import java.util.List;

public interface PokemonRepository {
    List<Pokemon> findAll();
}
