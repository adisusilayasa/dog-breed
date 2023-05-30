package com.dogbreed.dogbreed.models.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dogbreed.models.entities.DogEntity;
import com.dogbreed.models.entities.DogImageEntity;

public class DogImageEntityTest {

    @Test
    public void testGettersAndSetters() {
        // Create a DogImageEntity instance
        DogImageEntity dogImageEntity = new DogImageEntity();

        // Set values using setters
        Long id = 1L;
        String imageUrl = "https://example.com/image.jpg";
        DogEntity dogEntity = new DogEntity();

        dogImageEntity.setId(id);
        dogImageEntity.setImageUrl(imageUrl);
        dogImageEntity.setDogEntity(dogEntity);

        // Verify values using getters
        assertEquals(id, dogImageEntity.getId());
        assertEquals(imageUrl, dogImageEntity.getImageUrl());
        assertEquals(dogEntity, dogImageEntity.getDogEntity());
    }
}
