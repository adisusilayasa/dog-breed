package com.dogbreed.dogbreed;

import com.dogbreed.DogBreedApplication;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DogBreedApplicationTests {

    @Test
    void modelMapper_ShouldReturnNonNullModelMapper() {
        // Arrange
        DogBreedApplication application = new DogBreedApplication();

        // Act
        ModelMapper modelMapper = application.modelMapper();

        // Assert
        assertNotNull(modelMapper);
    }
}
