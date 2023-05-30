package com.dogbreed.dogbreed.helpers;

import com.dogbreed.helpers.ImageFilter;
import com.dogbreed.helpers.OddImageFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OddImageFilterTest {

    private ImageFilter imageFilter;

    @BeforeEach
    void setUp() {
        imageFilter = new OddImageFilter();
    }

    @Test
    void filterImages_ShouldReturnOddNumberedImages() {
        // Arrange
        List<String> imageUrls = Arrays.asList(
                "image1.jpg",
                "image2.jpg",
                "image3.jpg",
                "image4.jpg",
                "image5.jpg"
        );

        // Act
        List<String> filteredImages = imageFilter.filterImages(imageUrls);

        // Assert
        List<String> expectedImages = Arrays.asList("image1.jpg", "image3.jpg", "image5.jpg");
        assertEquals(expectedImages, filteredImages);
    }

    @Test
    void filterImages_ShouldReturnEmptyList_WhenNoImagesProvided() {
        // Arrange
        List<String> imageUrls = Arrays.asList();

        // Act
        List<String> filteredImages = imageFilter.filterImages(imageUrls);

        // Assert
        List<String> expectedImages = Arrays.asList();
        assertEquals(expectedImages, filteredImages);
    }
}
