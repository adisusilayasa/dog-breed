package com.dogbreed.controllers;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dogbreed.dto.DogDetailsDto;
import com.dogbreed.models.entities.DogEntity;
import com.dogbreed.models.responses.CustomResponse;
import com.dogbreed.services.DogImageFetcher;
import com.dogbreed.services.DogService;
import com.dogbreed.services.MapDogService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/dogs")
public class DogController {

    @Autowired
    private DogService dogService;
    
    @Autowired
    private DogImageFetcher dogImageFetcher;

    @Autowired
    private MapDogService mapDogService;
    
    @PostMapping("/fetch-dogs")
    public ResponseEntity<CustomResponse<List<DogEntity>>> fetchAndSaveFromApi() {
        try {
            dogService.fetchAndSaveDataFromApi();
            dogImageFetcher.fetchAndSaveDogImages();
            return ResponseEntity.ok(new CustomResponse<>(HttpStatus.OK.value(), "Dog retrieved successfully.","success",  null));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomResponse<>(HttpStatus.NOT_FOUND.value(),  "Dog not found.","failed", null));
        }    
    }

    @GetMapping
    public ResponseEntity<CustomResponse<Iterable<DogEntity>>> getAllDogs() {
        Iterable<DogEntity> dogs = dogService.findAllDogs();
        if (dogs == null || !dogs.iterator().hasNext()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new CustomResponse<>(HttpStatus.NO_CONTENT.value(), "No dogs found.","failed",  null));
        }
        return ResponseEntity.ok(new CustomResponse<>(HttpStatus.OK.value(),  "Dogs retrieved successfully.","success", dogs));
    }

    @PutMapping("/{breed}")
    public ResponseEntity<CustomResponse<DogEntity>> updateDogBreed(
            @PathVariable("breed") @NotBlank String breed,
            @Valid @RequestBody DogEntity dogEntity) {

        dogService.updateDogBreedData(breed, dogEntity);
        return ResponseEntity.ok(new CustomResponse<>(HttpStatus.OK.value(), "Dog breed updated successfully.", "success", null));
    }
    
    @DeleteMapping("/{breed}")
    public ResponseEntity<CustomResponse<DogEntity>> deleteDog(@Valid @PathVariable("breed") String breed){
        dogService.deleteDogByBreed(breed);
        return ResponseEntity.ok(new CustomResponse<>(HttpStatus.OK.value(),  "Dog deleted successfully.","success", null));
    }

    @GetMapping("/{breed}")
    public ResponseEntity<CustomResponse<DogEntity>> getDogDataByBreed(@Valid @PathVariable String breed) {
        try {
            DogEntity dog = dogService.findDogByBreed(breed);
            return ResponseEntity.ok(new CustomResponse<>(HttpStatus.OK.value(),  "Dog retrieved successfully.","success", dog));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomResponse<>(HttpStatus.NOT_FOUND.value(),  "Dog not found.","failed", null));
        }
    }

    @GetMapping("/images")
    public Map<String, DogDetailsDto> getDogData() {
        return mapDogService.mapDogService();
    }

    @GetMapping("image/{breed}")
    public ResponseEntity<CustomResponse<Map<String, DogDetailsDto>>> getDogDataImageByBreed(@Valid @PathVariable String breed) {
        try {
            Map<String, DogDetailsDto> dog = mapDogService.mapDogServiceByBreed(breed);
            return ResponseEntity.ok(new CustomResponse<>(HttpStatus.OK.value() ,"Dog retrieved successfully.","success",  dog));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomResponse<>(HttpStatus.NOT_FOUND.value(),  "Dog not found.", "failed",null));
        }
    }

}
