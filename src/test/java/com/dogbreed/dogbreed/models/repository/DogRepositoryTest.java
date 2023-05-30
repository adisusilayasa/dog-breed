package com.dogbreed.dogbreed.models.repository;
import com.dogbreed.models.entities.DogEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DogRepositoryTest {

    @Mock
    private CrudRepository<DogEntity, Long> dogRepository;

    @Test
    void testFindByBreed() {
        // Create a mock DogEntity
        DogEntity dogEntity = new DogEntity();
        dogEntity.setBreed("Bulldog");

        // Mock the behavior of the CrudRepository
        when(dogRepository.findById(1L)).thenReturn(Optional.of(dogEntity));

        // Perform the findByBreed operation
        Optional<DogEntity> result = dogRepository.findById(1L);

        // Verify the interactions
        verify(dogRepository).findById(1L);
        assertEquals(Optional.of(dogEntity), result);
    }

    @Test
    void testDeleteByBreed() {
        // Perform the deleteByBreed operation
        dogRepository.deleteById(1L);

        // Verify the interactions
        verify(dogRepository).deleteById(1L);
    }

    // @Test
    // void testFlush() {
    //     // Perform the flush operation
    //     dogRepository.();

    //     // Verify the interactions
    //     verify(dogRepository).flush();
    // }
}
