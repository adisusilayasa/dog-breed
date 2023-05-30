package com.dogbreed.dogbreed.controller;

import com.dogbreed.controllers.DogController;
import com.dogbreed.dto.DogDetailsDto;
import com.dogbreed.models.entities.DogEntity;
import com.dogbreed.models.responses.CustomResponse;
import com.dogbreed.services.DogImageFetcher;
import com.dogbreed.services.DogService;
import com.dogbreed.services.MapDogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DogControllerTest {

    @Mock
    private DogService dogService;

    @Mock
    private DogImageFetcher dogImageFetcher;

    @Mock
    private MapDogService mapDogService;

    @InjectMocks
    private DogController dogController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchAndSaveFromApi() {
        // Perform the fetchAndSaveFromApi operation
        ResponseEntity<CustomResponse<List<DogEntity>>> response = dogController.fetchAndSaveFromApi();

        // Verify the interactions
        verify(dogService).fetchAndSaveDataFromApi();
        verify(dogImageFetcher).fetchAndSaveDogImages();

        // Assert the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().getStatus());
        assertEquals("Dog retrieved successfully.", response.getBody().getMessage());
        assertEquals(null, response.getBody().getPayload());
    }

    @Test
    void testGetAllDogs_noDogsFound() {
        // Mock the dog service's behavior
        when(dogService.findAllDogs()).thenReturn(null);

        // Perform the getAllDogs operation
        ResponseEntity<CustomResponse<Iterable<DogEntity>>> response = dogController.getAllDogs();

        // Verify the interactions
        verify(dogService).findAllDogs();

        // Assert the result
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("failed", response.getBody().getStatus());
        assertEquals("No dogs found.", response.getBody().getMessage());
        assertEquals(null, response.getBody().getPayload());
    }

    @Test
    void fetchAndSaveFromApi_ShouldReturnNotFoundResponse_WhenNoSuchElementExceptionOccurs() {

        doThrow(NoSuchElementException.class)
                .when(dogService)
                .fetchAndSaveDataFromApi();

        // Act
        ResponseEntity<CustomResponse<List<DogEntity>>> response = dogController.fetchAndSaveFromApi();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("failed", response.getBody().getStatus());
        assertEquals("Dog not found.", response.getBody().getMessage());
        assertEquals(null, response.getBody().getPayload());
    }

    @Test
    void testGetAllDogs_dogsFound() {
        // Mock dog entities
        List<DogEntity> dogEntities = new ArrayList<>();
        DogEntity dogEntity = new DogEntity();
        dogEntity.setBreed("Bulldog");
        dogEntities.add(dogEntity);

        // Mock the dog service's behavior
        when(dogService.findAllDogs()).thenReturn(dogEntities);

        // Perform the getAllDogs operation
        ResponseEntity<CustomResponse<Iterable<DogEntity>>> response = dogController.getAllDogs();

        // Verify the interactions
        verify(dogService).findAllDogs();

        // Assert the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().getStatus());
        assertEquals("Dogs retrieved successfully.", response.getBody().getMessage());
        assertEquals(dogEntities, response.getBody().getPayload());
    }

    @Test
    void testDeleteDog() {
        // Perform the deleteDog operation
        ResponseEntity<CustomResponse<DogEntity>> response = dogController.deleteDog("Bulldog");

        // Verify the interactions
        verify(dogService).deleteDogByBreed("Bulldog");

        // Assert the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().getStatus());
        assertEquals("Dog deleted successfully.", response.getBody().getMessage());
        assertEquals(null, response.getBody().getPayload());
    }

    @Test
    void testGetDogDataByBreed_dogFound() {
        // Mock dog entity
        DogEntity dogEntity = new DogEntity();
        dogEntity.setBreed("Bulldog");

        // Mock the dog service's behavior
        when(dogService.findDogByBreed("Bulldog")).thenReturn(dogEntity);

        // Perform the getDogDataByBreed operation
        ResponseEntity<CustomResponse<DogEntity>> response = dogController.getDogDataByBreed("Bulldog");

        // Verify the interactions
        verify(dogService).findDogByBreed("Bulldog");

        // Assert the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().getStatus());
        assertEquals("Dog retrieved successfully.", response.getBody().getMessage());
        assertEquals(dogEntity, response.getBody().getPayload());
    }

    @Test
    void testGetDogDataByBreed_dogNotFound() {
        // Mock the dog service's behavior
        when(dogService.findDogByBreed("Bulldog")).thenThrow(NoSuchElementException.class);

        // Perform the getDogDataByBreed operation
        ResponseEntity<CustomResponse<DogEntity>> response = dogController.getDogDataByBreed("Bulldog");

        // Verify the interactions
        verify(dogService).findDogByBreed("Bulldog");

        // Assert the result
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("failed", response.getBody().getStatus());
        assertEquals("Dog not found.", response.getBody().getMessage());
        assertEquals(null, response.getBody().getPayload());
    }

    @Test
    void testGetDogData() {
        // Mock map dog service's behavior
        Map<String, DogDetailsDto> dogData = new HashMap<>();
        DogDetailsDto dogDetailsDto = new DogDetailsDto();
        dogDetailsDto.setDogImages(Arrays.asList("url1", "url2"));
        dogData.put("Bulldog", dogDetailsDto);
        when(mapDogService.mapDogService()).thenReturn(dogData);

        // Perform the getDogData operation
        Map<String, DogDetailsDto> result = dogController.getDogData();

        // Verify the interactions
        verify(mapDogService).mapDogService();

        // Assert the result
        assertEquals(dogData, result);
    }

    @Test
    void testGetDogDataImageByBreed_dogFound() {
        // Mock map dog service's behavior
        Map<String, DogDetailsDto> dogData = new HashMap<>();
        DogDetailsDto dogDetailsDto = new DogDetailsDto();
        dogDetailsDto.setDogImages(Arrays.asList("url1", "url2"));
        dogData.put("Bulldog", dogDetailsDto);
        when(mapDogService.mapDogServiceByBreed("Bulldog")).thenReturn(dogData);

        // Perform the getDogDataImageByBreed operation
        ResponseEntity<CustomResponse<Map<String, DogDetailsDto>>> response = dogController.getDogDataImageByBreed("Bulldog");

        // Verify the interactions
        verify(mapDogService).mapDogServiceByBreed("Bulldog");

        // Assert the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().getStatus());
        assertEquals("Dog retrieved successfully.", response.getBody().getMessage());
        assertEquals(dogData, response.getBody().getPayload());
    }

    @Test
    void testGetDogDataImageByBreed_dogNotFound() {
        // Mock map dog service's behavior
        when(mapDogService.mapDogServiceByBreed("Bulldog")).thenThrow(NoSuchElementException.class);

        // Perform the getDogDataImageByBreed operation
        ResponseEntity<CustomResponse<Map<String, DogDetailsDto>>> response = dogController.getDogDataImageByBreed("Bulldog");

        // Verify the interactions
        verify(mapDogService).mapDogServiceByBreed("Bulldog");

        // Assert the result
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("failed", response.getBody().getStatus());
        assertEquals("Dog not found.", response.getBody().getMessage());
        assertEquals(null, response.getBody().getPayload());
    }
}
