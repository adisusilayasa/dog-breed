package com.dogbreed.services;

import com.dogbreed.helpers.ImageFilter;
import com.dogbreed.models.entities.DogEntity;
import com.dogbreed.models.entities.DogImageApiResponse;
import com.dogbreed.models.entities.DogImageEntity;
import com.dogbreed.models.repository.DogImageRepository;
import com.dogbreed.models.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class DogImageFetcher {

    @Autowired
    private DogRepository dogRepository;
    @Autowired
    private DogImageRepository dogImageRepository;
    @Autowired
    private ImageFilter imageFilter;

    private static final String API_URL = "https://dog.ceo/api/breed/%s/images";

    public List<DogImageEntity> fetchAndSaveDogImages() {
        List<DogEntity> dogEntities = new ArrayList<>();
        dogRepository.findAll().forEach(dogEntities::add);
        
        List<CompletableFuture<List<DogImageEntity>>> futures = dogEntities.stream()
                .map(this::fetchDogImagesAsync)
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return futures.stream()
                .flatMap(future -> {
                    try {
                        return future.get().stream();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    public CompletableFuture<List<DogImageEntity>> fetchDogImagesAsync(DogEntity dog) {
        String breedName = dog.getBreed();
        String apiUrl = buildApiUrl(breedName);

        return CompletableFuture.supplyAsync(() -> {
            RestTemplate restTemplate = new RestTemplate();
            try {
                ResponseEntity<DogImageApiResponse> response = restTemplate.getForEntity(apiUrl,
                        DogImageApiResponse.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    DogImageApiResponse apiResponse = response.getBody();
                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
                        List<String> imageUrls = apiResponse.getMessage();

                        // Apply image filter strategy
                        imageUrls = imageFilter.filterImages(imageUrls);

                        List<DogImageEntity> breedDogImages = imageUrls.stream()
                                .map(imageUrl -> {
                                    DogImageEntity dogImageEntity = new DogImageEntity();
                                    dogImageEntity.setImageUrl(imageUrl);
                                    dogImageEntity.setDogEntity(dog);
                                    return dogImageEntity;
                                })
                                .collect(Collectors.toList());

                        dogImageRepository.saveAll(breedDogImages);

                        return breedDogImages;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return List.of();
        });
    }

    private String buildApiUrl(String breedName) {
        if (breedName.contains("-")) {
            String[] breedParts = breedName.split("-");
            return String.format(API_URL, breedParts[0]);
        } else {
            return String.format(API_URL, breedName);
        }
    }
}
