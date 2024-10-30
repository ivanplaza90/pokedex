package com.ivan.pokedex.infrastructure.api.rest.pokemon.get;


import com.ivan.pokedex.application.pokemon.get.GetPokemon;
import com.ivan.pokedex.application.pokemon.get.GetPokemonQuery;
import com.ivan.pokedex.application.pokemon.get.PokemonView;
import com.ivan.pokedex.domain.exception.PokemonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class GetPokemonResource {

    private final GetPokemon getPokemon;

    public GetPokemonResource(final GetPokemon getPokemon) {
        this.getPokemon = getPokemon;
    }

    @GetMapping("/pokemon/{pokemonNumber}")
    public PokemonDTO get(@PathVariable final Integer pokemonNumber) {
        final GetPokemonQuery query = new GetPokemonQuery(pokemonNumber);
        return getPokemon.apply(query)
            .map(view -> mapPokemon(view))
            .orElseThrow(() -> new PokemonNotFoundException());
    }

    private PokemonDTO mapPokemon(final PokemonView view) {
        return new PokemonDTO(view.number(), view.name(), view.type(), view.combatPoints(), view.healthPoints());
    }

    @ExceptionHandler(PokemonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void PokemonNotFoundHandler() {

    }


}
