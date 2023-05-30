package com.dogbreed.services;

import com.dogbreed.models.entities.DogEntity;
import com.dogbreed.models.entities.DogImageApiResponse;
import com.dogbreed.models.entities.DogImageEntity;
import com.dogbreed.models.entities.DogApiResponse;
import com.dogbreed.models.repository.DogImageRepository;
import com.dogbreed.models.repository.DogRepository;
import com.dogbreed.models.responses.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Service
@Transactional
public class DogService {

    private static final String API_URL = "https://dog.ceo/api/breeds/list/all";

    private final DogRepository dogRepository;
    private final DogImageRepository dogImageRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public DogService(DogRepository dogRepository, DogImageRepository dogImageRepository, RestTemplate restTemplate) {
        this.dogRepository = dogRepository;
        this.dogImageRepository = dogImageRepository;
        this.restTemplate = restTemplate;
    }

    public void fetchAndSaveDataFromApi() {
        try {
            ResponseEntity<DogApiResponse> response = restTemplate.exchange(API_URL, HttpMethod.GET, null,
                    new ParameterizedTypeReference<DogApiResponse>() {});

            DogApiResponse apiResponse = response.getBody();

            if (apiResponse != null && apiResponse.getStatus().equals("success")) {
                Map<String, List<String>> breeds = apiResponse.getMessage();

                dogRepository.deleteAll();

                List<CompletableFuture<DogEntity>> futures = new ArrayList<>();

                for (Map.Entry<String, List<String>> entry : breeds.entrySet()) {
                    String breedName = entry.getKey();
                    List<String> subBreeds = entry.getValue();

                    if (subBreeds != null && !subBreeds.isEmpty()) {
                        for (String subBreed : subBreeds) {
                            String breedWithSubBreed = breedName + "-" + subBreed;
                            CompletableFuture<DogEntity> future = createDogEntity(breedWithSubBreed);
                            futures.add(future);
                        }
                    } else {
                        CompletableFuture<DogEntity> future = createDogEntity(breedName);
                        futures.add(future);
                    }
                }

                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

                List<DogEntity> dogEntities = futures.stream()
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                dogRepository.saveAll(dogEntities);
            }
        } catch (HttpClientErrorException.NotFound ex) {
            throw new CustomException("API endpoint not found. Error message: " + ex.getMessage());
        } catch (Exception ex) {
            throw new CustomException("An error occurred while fetching data from the API. Error message: " + ex.getMessage());
        }
    }


    public CompletableFuture<DogEntity> createDogEntity(String breed) {
        return CompletableFuture.supplyAsync(() -> {
            DogEntity dogEntity = new DogEntity();
            dogEntity.setBreed(breed);
            return dogEntity;
        });
    }

    public void fetchAndSaveDogImages() {
        List<DogImageEntity> dogImages = new ArrayList<>();

        List<DogEntity> dogEntities = new ArrayList<>();
        dogRepository.findAll().forEach(dogEntities::add);

        Logger logger = LoggerFactory.getLogger(DogService.class);

        for (DogEntity dog : dogEntities) {
            String breedName = dog.getBreed();

            logger.info(dog.getBreed());

            String apiUrl = "https://dog.ceo/api/breed/" + breedName + "/images";

            try {
                ResponseEntity<DogImageApiResponse> response = restTemplate.getForEntity(apiUrl,
                        DogImageApiResponse.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    DogImageApiResponse apiResponse = response.getBody();
                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
                        List<String> imageUrls = apiResponse.getMessage();

                        for (String imageUrl : imageUrls) {
                            DogImageEntity dogImageEntity = new DogImageEntity();
                            dogImageEntity.setImageUrl(imageUrl);
                            dogImageEntity.setDogEntity(dog);
                            dogImageRepository.save(dogImageEntity);
                            dogImages.add(dogImageEntity);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Iterable<DogEntity> findAllDogs() {
        return dogRepository.findAll();
    }

    public DogEntity findDogByBreed(String breed) {
        return dogRepository.findByBreed(breed);
    }

    public void deleteDogByBreed(String breed) {
        dogRepository.deleteByBreed(breed);
    }

    public void updateDogBreedData(@NotBlank String breed, @Valid DogEntity dogEntity) {
        DogEntity existingDog = dogRepository.findByBreed(breed);
        if (existingDog == null) {
            throw new CustomException("Dog breed not found: " + breed);
        }

        existingDog.setBreed(dogEntity.getBreed());
        // Update other properties of DogEntity as needed

        dogRepository.save(existingDog);
    }

}
