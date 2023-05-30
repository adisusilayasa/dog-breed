package com.dogbreed.dogbreed.models.repository;

import com.dogbreed.models.entities.DogImageEntity;
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
class DogImageRepositoryTest {

    @Mock
    private CrudRepository<DogImageEntity, Long> dogImageRepository;

    @Test
    void testSave() {
        // Create a mock DogImageEntity
        DogImageEntity dogImageEntity = new DogImageEntity();
        dogImageEntity.setId(1L);

        // Mock the behavior of the CrudRepository
        when(dogImageRepository.save(dogImageEntity)).thenReturn(dogImageEntity);

        // Perform the save operation
        DogImageEntity result = dogImageRepository.save(dogImageEntity);

        // Verify the interactions
        verify(dogImageRepository).save(dogImageEntity);
        assertEquals(dogImageEntity, result);
    }

    @Test
    void testFindById() {
        // Create a mock DogImageEntity
        DogImageEntity dogImageEntity = new DogImageEntity();
        dogImageEntity.setId(1L);

        // Mock the behavior of the CrudRepository
        when(dogImageRepository.findById(1L)).thenReturn(Optional.of(dogImageEntity));

        // Perform the findById operation
        Optional<DogImageEntity> result = dogImageRepository.findById(1L);

        // Verify the interactions
        verify(dogImageRepository).findById(1L);
        assertEquals(Optional.of(dogImageEntity), result);
    }

    @Test
    void testDeleteById() {
        // Perform the deleteById operation
        dogImageRepository.deleteById(1L);

        // Verify the interactions
        verify(dogImageRepository).deleteById(1L);
    }
}
