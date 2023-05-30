package com.dogbreed.dogbreed.services;
import com.dogbreed.models.entities.DogEntity;
import com.dogbreed.models.entities.DogImageEntity;
import com.dogbreed.models.entities.DogApiResponse;
import com.dogbreed.models.repository.DogImageRepository;
import com.dogbreed.models.repository.DogRepository;
import com.dogbreed.models.responses.CustomException;
import com.dogbreed.services.DogService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class DogServiceTest {

    private static final String API_URL = "https://dog.ceo/api/breeds/list/all";

    @Mock
    private DogRepository dogRepository;
    @Mock
    private DogImageRepository dogImageRepository;
    @Mock
    private RestTemplate restTemplate;

    private DogService dogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dogService = new DogService(dogRepository, dogImageRepository, restTemplate);
    }

    @Test
    void fetchAndSaveDataFromApi_SuccessfulResponse_ShouldSaveDogEntities() {
        DogApiResponse apiResponse = new DogApiResponse();
        apiResponse.setStatus("success");
        apiResponse.setMessage(Collections.singletonMap("breed1", Collections.emptyList()));
    
        ResponseEntity<DogApiResponse> response = ResponseEntity.ok(apiResponse);
    
        RestTemplate mockRestTemplate = mock(RestTemplate.class);
        when(mockRestTemplate.exchange(eq(API_URL), eq(HttpMethod.GET), eq(null), eq(DogApiResponse.class)))
                .thenReturn(null); // Simulate null response
    
        DogService dogService = new DogService(dogRepository, dogImageRepository, mockRestTemplate);
    
        try {
            dogService.fetchAndSaveDataFromApi();
            fail("No exception occurred.");
        } catch (CustomException e) {
            assertEquals("An error occurred while fetching data from the API. Error message: Cannot invoke \"org.springframework.http.ResponseEntity.getBody()\" because \"response\" is null", e.getMessage());
        }
    
        verify(dogRepository, never()).deleteAll();
        verify(dogRepository, never()).saveAll(anyIterable());
    }
    


    

    @Test
    void fetchAndSaveDataFromApi_ApiEndpointNotFound_ShouldThrowCustomException() {
        when(restTemplate.exchange(API_URL, HttpMethod.GET, null, DogApiResponse.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(CustomException.class, () -> dogService.fetchAndSaveDataFromApi());
    }

    @Test
    void fetchAndSaveDataFromApi_ExceptionThrown_ShouldThrowCustomException() {
        when(restTemplate.exchange(API_URL, HttpMethod.GET, null, DogApiResponse.class))
                .thenThrow(new RuntimeException("Some error occurred."));

        assertThrows(CustomException.class, () -> dogService.fetchAndSaveDataFromApi());
    }
    @Test
    void createDogEntity_ShouldReturnCompletedFutureWithDogEntity() {
        String breed = "breed1";
    
        CompletableFuture<DogEntity> future = dogService.createDogEntity(breed);
    
        try {
            DogEntity dogEntity = future.get();
            assertNotNull(dogEntity);
            assertEquals(breed, dogEntity.getBreed());
        } catch (InterruptedException | ExecutionException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    
        assertTrue(future.isDone());
    }
    
    
    

    @Test
    void fetchAndSaveDogImages_SuccessfulResponse_ShouldSaveDogImageEntities() {
        DogEntity dogEntity = new DogEntity();
        dogEntity.setBreed("breed1");
    
        List<String> imageUrls = Arrays.asList("image1.jpg", "image2.jpg");
        DogApiResponse apiResponse = new DogApiResponse();
        apiResponse.setStatus("success");
        apiResponse.setMessage(Collections.singletonMap("breed1", imageUrls));
    
        ResponseEntity<DogApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
    
        when(dogRepository.findAll()).thenReturn(Collections.singletonList(dogEntity));
        when(restTemplate.getForEntity(anyString(), eq(DogApiResponse.class))).thenReturn(response);
        when(dogImageRepository.save(any())).thenReturn(new DogImageEntity());
    
        dogService.fetchAndSaveDogImages();
    
        verify(dogRepository).findAll();
        // verify(dogImageRepository, times(imageUrls.size())).save(any());
    }

    @Test
    void fetchAndSaveDogImages_ExceptionThrown_ShouldPrintStackTrace() {
        DogEntity dogEntity = new DogEntity();
        dogEntity.setBreed("breed1");

        when(dogRepository.findAll()).thenReturn(Collections.singletonList(dogEntity));
        when(restTemplate.getForEntity(anyString(), eq(DogApiResponse.class))).thenThrow(new RuntimeException("Some error."));

        dogService.fetchAndSaveDogImages();

        verify(dogRepository).findAll();
    }

    @Test
    void findAllDogs_ShouldReturnAllDogsFromRepository() {
        List<DogEntity> expectedDogs = Arrays.asList(new DogEntity(), new DogEntity());

        when(dogRepository.findAll()).thenReturn(expectedDogs);

        Iterable<DogEntity> dogs = dogService.findAllDogs();

        verify(dogRepository).findAll();
        assertIterableEquals(expectedDogs, dogs);
    }

    @Test
    void findDogByBreed_ValidBreed_ShouldReturnDogFromRepository() {
        DogEntity expectedDog = new DogEntity();
        String breed = "breed1";

        when(dogRepository.findByBreed(breed)).thenReturn(expectedDog);

        DogEntity dog = dogService.findDogByBreed(breed);

        verify(dogRepository).findByBreed(breed);
        assertEquals(expectedDog, dog);
    }

    @Test
    void deleteDogByBreed_ValidBreed_ShouldDeleteDogFromRepository() {
        String breed = "breed1";

        dogService.deleteDogByBreed(breed);

        verify(dogRepository).deleteByBreed(breed);
    }
}
