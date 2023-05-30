package com.dogbreed.dogbreed.models.entities;

import com.dogbreed.models.entities.DogImageApiResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DogImageApiResponseTest {

    @Test
    void setStatusAndGetStatus_ShouldSetAndRetrieveStatusCorrectly() {
        // Arrange
        DogImageApiResponse response = new DogImageApiResponse();

        // Act
        response.setStatus("success");

        // Assert
        assertEquals("success", response.getStatus());
    }

    @Test
    void setMessageAndGetMessage_ShouldSetAndRetrieveMessageCorrectly() {
        // Arrange
        DogImageApiResponse response = new DogImageApiResponse();
        List<String> message = Arrays.asList("url1", "url2", "url3");

        // Act
        response.setMessage(message);

        // Assert
        assertEquals(message, response.getMessage());
    }

    @Test
    void setStatus_ShouldSetNullStatus() {
        // Arrange
        DogImageApiResponse response = new DogImageApiResponse();

        // Act
        response.setStatus(null);

        // Assert
        assertNull(response.getStatus());
    }

    @Test
    void setMessage_ShouldSetNullMessage() {
        // Arrange
        DogImageApiResponse response = new DogImageApiResponse();

        // Act
        response.setMessage(null);

        // Assert
        assertNull(response.getMessage());
    }
}
