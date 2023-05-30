package com.dogbreed.dogbreed.helpers;
import com.dogbreed.helpers.ImageFilter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImageFilterTest {

    private class MockImageFilter implements ImageFilter {
        @Override
        public List<String> filterImages(List<String> imageUrls) {
            // Mock implementation that adds a prefix to each image URL
            for (int i = 0; i < imageUrls.size(); i++) {
                imageUrls.set(i, "filtered_" + imageUrls.get(i));
            }
            return imageUrls;
        }
    }

    @Test
    void filterImages_ShouldAddPrefixToImageUrls() {
        // Arrange
        List<String> imageUrls = Arrays.asList(
                "image1.jpg",
                "image2.jpg",
                "image3.jpg"
        );

        ImageFilter imageFilter = new MockImageFilter();

        // Act
        List<String> filteredImages = imageFilter.filterImages(imageUrls);

        // Assert
        List<String> expectedFilteredImages = Arrays.asList(
                "filtered_image1.jpg",
                "filtered_image2.jpg",
                "filtered_image3.jpg"
        );
        assertEquals(expectedFilteredImages, filteredImages);
    }

    @Test
    void filterImages_ShouldReturnEmptyList_WhenNoImagesProvided() {
        // Arrange
        List<String> imageUrls = Arrays.asList();

        ImageFilter imageFilter = new MockImageFilter();

        // Act
        List<String> filteredImages = imageFilter.filterImages(imageUrls);

        // Assert
        assertEquals(imageUrls, filteredImages);
    }
}
