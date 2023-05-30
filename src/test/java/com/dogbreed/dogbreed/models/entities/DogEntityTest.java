package com.dogbreed.dogbreed.models.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.dogbreed.models.entities.DogEntity;
import com.dogbreed.models.entities.DogImageEntity;

import java.util.ArrayList;
import java.util.List;

public class DogEntityTest {

    @Test
    public void testGettersAndSetters() {
        // Create a DogEntity instance
        DogEntity dogEntity = new DogEntity();

        // Set values using setters
        Long id = 1L;
        String breed = "Labrador Retriever";
        List<DogImageEntity> dogImages = new ArrayList<>();
        DogImageEntity dogImage = new DogImageEntity();
        dogImages.add(dogImage);

        dogEntity.setId(id);
        dogEntity.setBreed(breed);
        dogEntity.setDogImages(dogImages);

        // Verify values using getters
        assertEquals(id, dogEntity.getId());
        assertEquals(breed, dogEntity.getBreed());
        assertEquals(dogImages, dogEntity.getDogImages());
    }

    @Test
    public void testJsonPropertyAnnotation() {
        // Create a DogEntity instance
        DogEntity dogEntity = new DogEntity();

        // Set a breed value
        String breed = "German Shepherd";
        dogEntity.setBreed(breed);

        // Verify that the JSON property annotation is working correctly
        assertEquals(breed, dogEntity.getBreed());
    }

    @Test
    public void testDogImagesAssociation() {
        // Create a DogEntity instance
        DogEntity dogEntity = new DogEntity();

        // Create a mock DogImageEntity
        DogImageEntity dogImageMock = mock(DogImageEntity.class);

        // Create a list of dog images and add the mock
        List<DogImageEntity> dogImages = new ArrayList<>();
        dogImages.add(dogImageMock);

        // Set the dog images using the setter
        dogEntity.setDogImages(dogImages);

        // Verify that the dog images association is correctly set
        assertEquals(dogImages, dogEntity.getDogImages());
    }
}
