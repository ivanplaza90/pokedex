package com.ivan.pokedex.infrastructure.repository.mongo;

import com.ivan.pokedex.domain.Trainer;
import com.ivan.pokedex.infrastructure.repository.mongo.model.TrainerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerRepositoryMongoAdapterTest {

    private static final int TRAINER_ID = 1;

    @Mock
    private TrainerMongoRepository trainerMongoRepository;

    @InjectMocks
    private TrainerRepositoryMongoAdapter trainerRepositoryMongoAdapter;

    @Test
    void return_empty_given_trainer_id_when_repository_returns_empty() {
        when(trainerMongoRepository.findById(TRAINER_ID)).thenReturn(Optional.empty());

        final Optional<Trainer> response = trainerRepositoryMongoAdapter.get(TRAINER_ID);

        assertThat(response)
                .isEmpty();
    }

    @Test
    void return_trainer_given_trainer_id_when_repository_returns_a_trainer() {
        final TrainerEntity trainerEntity = mockTrainer(Set.of(1, 2, 3));
        when(trainerMongoRepository.findById(TRAINER_ID)).thenReturn(Optional.of(trainerEntity));

        final Optional<Trainer> response = trainerRepositoryMongoAdapter.get(TRAINER_ID);

        assertThat(response)
            .isNotEmpty()
            .get()
            .isEqualTo(new Trainer(trainerEntity.trainerId(), trainerEntity.favorites()));
    }

    @Test
    void save_trainer_when_mongo_repository_success() {
        final Trainer trainer = new Trainer(1, Set.of(1, 2, 3, 4));
        final TrainerEntity entity = new TrainerEntity(trainer.trainerId(), trainer.favorites());
        when(trainerMongoRepository.save(entity)).thenReturn(entity);

        trainerRepositoryMongoAdapter.save(trainer);

        verify(trainerMongoRepository).save(entity);
        verifyNoMoreInteractions(trainerMongoRepository);
    }

    private TrainerEntity mockTrainer(final Set favorites) {
        return new TrainerEntity(TRAINER_ID, favorites);
    }
}
