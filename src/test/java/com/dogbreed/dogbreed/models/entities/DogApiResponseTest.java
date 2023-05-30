package com.dogbreed.dogbreed.models.entities;

import com.dogbreed.models.entities.DogApiResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DogApiResponseTest {

    @Test
    void setStatusAndGetStatus_ShouldSetAndRetrieveStatusCorrectly() {
        // Arrange
        DogApiResponse response = new DogApiResponse();

        // Act
        response.setStatus("success");

        // Assert
        assertEquals("success", response.getStatus());
    }

    @Test
    void setMessageAndGetMessage_ShouldSetAndRetrieveMessageCorrectly() {
        // Arrange
        DogApiResponse response = new DogApiResponse();
        Map<String, List<String>> message = new HashMap<>();
        message.put("breed1", Arrays.asList("subBreed1", "subBreed2"));

        // Act
        response.setMessage(message);

        // Assert
        assertEquals(message, response.getMessage());
    }

    @Test
    void setStatus_ShouldSetNullStatus() {
        // Arrange
        DogApiResponse response = new DogApiResponse();

        // Act
        response.setStatus(null);

        // Assert
        assertNull(response.getStatus());
    }

    @Test
    void setMessage_ShouldSetNullMessage() {
        // Arrange
        DogApiResponse response = new DogApiResponse();

        // Act
        response.setMessage(null);

        // Assert
        assertNull(response.getMessage());
    }
}
