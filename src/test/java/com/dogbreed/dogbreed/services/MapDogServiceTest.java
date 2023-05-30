package com.dogbreed.dogbreed.services;

import com.dogbreed.dto.DogDetailsDto;
import com.dogbreed.models.entities.DogEntity;
import com.dogbreed.models.entities.DogImageEntity;
import com.dogbreed.models.repository.DogRepository;
import com.dogbreed.services.MapDogService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MapDogServiceTest {

    @Mock
    private DogRepository dogRepository;

    @InjectMocks
    private MapDogService mapDogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // @Test
    // void testMapDogService() {
    //     // Mock dog entities
    //     List<DogEntity> dogEntities = new ArrayList<>();

    //     DogEntity dogEntity1 = new DogEntity();
    //     dogEntity1.setBreed("Bulldog");
    //     List<DogImageEntity> dogImages1 = new ArrayList<>();

    //     DogImageEntity dogUrl = new DogImageEntity();
    //     dogUrl.setImageUrl("url1");

    //     DogImageEntity dogUrl1 = new DogImageEntity();
    //     dogUrl1.setImageUrl("url2");

    //     dogImages1.add(dogUrl);
    //     dogImages1.add(dogUrl1);
    //     dogEntity1.setDogImages(dogImages1);
    //     dogEntities.add(dogEntity1);
    //     DogEntity dogEntity2 = new DogEntity();
    //     dogEntity2.setBreed("Labrador");
    //     List<DogImageEntity> dogImages2 = new ArrayList<>();

    //     DogImageEntity dogUrl3 = new DogImageEntity();
    //     dogUrl3.setImageUrl("url3");

    //     DogImageEntity dogUrl4 = new DogImageEntity();
    //     dogUrl4.setImageUrl("url4");

    //     dogImages2.add(dogUrl3);
    //     dogImages2.add(dogUrl4);
    //     dogEntity2.setDogImages(dogImages2);
    //     dogEntities.add(dogEntity2);

    //     // Mock the dog repository's behavior
    //     when(dogRepository.findAll()).thenReturn(dogEntities);

    //     // Perform the mapDogService operation
    //     Map<String, DogDetailsDto> dogData = mapDogService.mapDogService();

    //     // Verify the interactions
    //     verify(dogRepository).findAll();

    //     // Assert the result
    //     assertEquals(2, dogData.size());
    //     assertEquals(2, dogData.get("Bulldog").getDogImages().size());
    //     assertEquals(2, dogData.get("Labrador").getDogImages().size());
    // }

    @Test
    void testMapDogServiceByBreed() {
        // Mock dog entities
        List<DogEntity> dogEntities = new ArrayList<>();

        DogEntity dogEntity1 = new DogEntity();
        dogEntity1.setBreed("Bulldog");
        List<DogImageEntity> dogImages1 = new ArrayList<>();

        DogImageEntity dogUrl = new DogImageEntity();
        dogUrl.setImageUrl("url1");

        DogImageEntity dogUrl1 = new DogImageEntity();
        dogUrl1.setImageUrl("url2");

        dogImages1.add(dogUrl);
        dogImages1.add(dogUrl1);
        dogEntity1.setDogImages(dogImages1);
        dogEntities.add(dogEntity1);
        DogEntity dogEntity2 = new DogEntity();
        dogEntity2.setBreed("Labrador");
        List<DogImageEntity> dogImages2 = new ArrayList<>();

        DogImageEntity dogUrl3 = new DogImageEntity();
        dogUrl3.setImageUrl("url3");

        DogImageEntity dogUrl4 = new DogImageEntity();
        dogUrl4.setImageUrl("url4");

        dogImages2.add(dogUrl3);
        dogImages2.add(dogUrl4);
        dogEntity2.setDogImages(dogImages2);
        dogEntities.add(dogEntity2);

        // Mock the dog repository's behavior
        when(dogRepository.findAll()).thenReturn(dogEntities);

        // Perform the mapDogServiceByBreed operation
        Map<String, DogDetailsDto> dogData = mapDogService.mapDogServiceByBreed("Bulldog");

        // Verify the interactions
        verify(dogRepository).findAll();

        // Assert the result
        assertEquals(1, dogData.size());
        assertEquals(2, dogData.get("Bulldog").getDogImages().size());
    }
}
