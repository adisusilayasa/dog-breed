package com.dogbreed.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogbreed.dto.DogDetailsDto;
import com.dogbreed.models.entities.DogEntity;
import com.dogbreed.models.entities.DogImageEntity;
import com.dogbreed.models.repository.DogRepository;

@Service
public class MapDogService {

    @Autowired
    private DogRepository dogRepository;

    public Map<String, DogDetailsDto> mapDogService() {
        Iterable<DogEntity> allDogData = dogRepository.findAll();
        Map<String, DogDetailsDto> dogData = new HashMap<>();

        for (DogEntity dogEntity : allDogData) {
            if (dogEntity.getBreed().isEmpty()) {
                DogDetailsDto dogDetails = new DogDetailsDto();
                List<String> dogImages = new ArrayList<>();
                for (DogImageEntity dogImageEntity : dogEntity.getDogImages()) {
                    dogImages.add(dogImageEntity.getImageUrl());
                }
                dogDetails.setDogImages(dogImages);
                dogData.put(dogEntity.getBreed(), dogDetails);
            }
        }

        return dogData;
    }
    public Map<String, DogDetailsDto> mapDogServiceByBreed(String breed) {
        Iterable<DogEntity> allDogData = dogRepository.findAll();
        Map<String, DogDetailsDto> dogData = new HashMap<>();
    
        for (DogEntity dogEntity : allDogData) {
            if (dogEntity.getBreed().equals(breed)) {
                DogDetailsDto dogDetails = new DogDetailsDto();
                List<String> dogImages = new ArrayList<>();
                for (DogImageEntity dogImageEntity : dogEntity.getDogImages()) {
                    dogImages.add(dogImageEntity.getImageUrl());
                }
                dogDetails.setDogImages(dogImages);
                dogData.put(breed, dogDetails);
                break;
            }
        }
    
        return dogData;
    }
    
}
