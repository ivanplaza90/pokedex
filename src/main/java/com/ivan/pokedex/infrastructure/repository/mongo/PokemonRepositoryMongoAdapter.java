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

    public PokemonRepositoryMongoAdapter(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Pokemon> search(final SearchPokemonCriteria criteria) {
        Query build = build(criteria);
        return mongoTemplate.find(build, PokemonEntity.class)
            .stream()
            .map(entity -> new Pokemon(entity.number(), entity.name(), PokemonType.valueOf(entity.type())))
            .collect(Collectors.toList());
    }

    private Query build(final SearchPokemonCriteria criteria) {
        final Query query = new Query();
        criteria.type().ifPresent(value -> query.addCriteria(Criteria.where("type").is(value.toString())));
        criteria.name().ifPresent(value -> query.addCriteria(Criteria.where("name").regex(".*" + value + ".*")));

        return query;
    }
}
