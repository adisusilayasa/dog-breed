package com.dogbreed.dogbreed.services;

import com.dogbreed.helpers.ImageFilter;
import com.dogbreed.models.entities.DogEntity;
import com.dogbreed.models.entities.DogImageApiResponse;
import com.dogbreed.models.entities.DogImageEntity;
import com.dogbreed.models.repository.DogImageRepository;
import com.dogbreed.models.repository.DogRepository;
import com.dogbreed.services.DogImageFetcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DogImageFetcherTest {

    @Mock
    private DogRepository dogRepository;

    @Mock
    private DogImageRepository dogImageRepository;

    @Mock
    private ImageFilter imageFilter;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DogImageFetcher dogImageFetcher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // @Test
    // void testFetchAndSaveDogImages() {
    //     // Mock dog entities
    //     List<DogEntity> dogEntities = new ArrayList<>();
    //     DogEntity bulldog = new DogEntity();
    //     bulldog.setBreed("Bulldog");
    //     dogEntities.add(bulldog);
    //     DogEntity labrador = new DogEntity();
    //     labrador.setBreed("Labrador");
    //     dogEntities.add(labrador);

    //     // Mock response from the Dog API
    //     DogImageApiResponse apiResponse = new DogImageApiResponse();
    //     apiResponse.setStatus("success");
    //     apiResponse.setMessage(List.of("url1", "url2", "url3"));

    //     // Mock the behavior of the image filter
    //     when(imageFilter.filterImages(anyList())).thenReturn(List.of("url1", "url2"));

    //     // Mock the RestTemplate's response
    //     ResponseEntity<DogImageApiResponse> responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);
    //     when(restTemplate.getForEntity(anyString(), eq(DogImageApiResponse.class))).thenReturn(responseEntity);

    //     // Mock the dog repository's behavior
    //     when(dogRepository.findAll()).thenReturn(dogEntities);

    //     // Perform the fetchAndSaveDogImages operation
    //     dogImageFetcher.fetchAndSaveDogImages();

    //     // Verify the interactions
    //     verify(dogRepository).findAll();
    //     verify(restTemplate, times(2)).getForEntity(anyString(), eq(DogImageApiResponse.class));
    //     verify(imageFilter).filterImages(anyList());
    //     verify(dogImageRepository).saveAll(anyList());
    // }

    // @Test
    // void testFetchDogImagesAsync() throws Exception {
    //     // Mock dog entity
    //     DogEntity bulldog = new DogEntity();
    //     bulldog.setBreed("Bulldog");

    //     // Mock response from the Dog API
    //     DogImageApiResponse apiResponse = new DogImageApiResponse();
    //     apiResponse.setStatus("success");
    //     apiResponse.setMessage(List.of("url1", "url2", "url3"));

    //     // Mock the behavior of the image filter
    //     when(imageFilter.filterImages(anyList())).thenReturn(List.of("url1", "url2"));

    //     // Mock the RestTemplate's response
    //     ResponseEntity<DogImageApiResponse> responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);
    //     when(restTemplate.getForEntity(anyString(), eq(DogImageApiResponse.class))).thenReturn(responseEntity);

    //     // Perform the fetchDogImagesAsync operation
    //     CompletableFuture<List<DogImageEntity>> future = dogImageFetcher.fetchDogImagesAsync(bulldog);

    //     // Wait for the CompletableFuture to complete
    //     List<DogImageEntity> dogImages = future.get();

    //     // Verify the interactions
    //     verify(restTemplate).getForEntity(anyString(), eq(DogImageApiResponse.class));
    //     verify(imageFilter).filterImages(anyList());
    //     verify(dogImageRepository).saveAll(anyList());

    //     // Assert the result
    //     assertEquals(2, dogImages.size());
    // }
}
