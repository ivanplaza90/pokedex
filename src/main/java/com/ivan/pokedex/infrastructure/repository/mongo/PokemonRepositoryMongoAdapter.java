package com.ivan.pokedex.infrastructure.repository.mongo;

import com.ivan.pokedex.domain.Pokemon;
import com.ivan.pokedex.domain.PokemonRepository;
import com.ivan.pokedex.domain.PokemonType;
import com.ivan.pokedex.domain.SearchPokemonCriteria;
import com.ivan.pokedex.infrastructure.repository.mongo.model.PokemonEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PokemonRepositoryMongoAdapter implements PokemonRepository {


    private final MongoTemplate mongoTemplate;
    private PokemonMongoRepository pokemonMongoRepository;

    public PokemonRepositoryMongoAdapter(
        final MongoTemplate mongoTemplate,
        final PokemonMongoRepository pokemonMongoRepository
    ) {
        this.mongoTemplate = mongoTemplate;
        this.pokemonMongoRepository = pokemonMongoRepository;
    }

    @Override
    public List<Pokemon> search(final SearchPokemonCriteria criteria) {
        Query build = build(criteria);
        return mongoTemplate.find(build, PokemonEntity.class)
            .stream()
            .map(entity -> mapPokemon(entity))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Pokemon> get(int pokemonNumber) {
        return pokemonMongoRepository.findById(pokemonNumber)
            .map(entity -> mapPokemon(entity));
    }

    private Query build(final SearchPokemonCriteria criteria) {
        final Query query = new Query();
        criteria.type().ifPresent(value -> query.addCriteria(Criteria.where("type").is(value.toString())));
        criteria.name().ifPresent(value -> query.addCriteria(Criteria.where("name").regex(".*" + value + ".*")));

        return query;
    }

    private static Pokemon mapPokemon(PokemonEntity entity) {
        return new Pokemon(entity.number(), entity.name(), PokemonType.valueOf(entity.type()), entity.combatPoints(), entity.healthPoints());
    }
}
