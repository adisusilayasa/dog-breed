package com.dogbreed.dogbreed.models.responses;

import com.dogbreed.models.responses.CustomResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CustomResponseTest {

    @Test
    void constructor_ShouldSetFieldsCorrectly() {
        // Arrange
        int code = 200;
        String status = "success";
        String message = "Data retrieved successfully.";
        String payload = "payload";

        // Act
        CustomResponse<String> response = new CustomResponse<>(code, message, status, payload);

        // Assert
        assertEquals(code, response.getCode());
        assertEquals(status, response.getStatus());
        assertEquals(message, response.getMessage());
        assertEquals(payload, response.getPayload());
    }

    @Test
    void settersAndGetters_ShouldSetAndRetrieveFieldsCorrectly() {
        // Arrange
        CustomResponse<Integer> response = new CustomResponse<>(200, "success", "Data retrieved successfully.", null);

        // Act
        response.setCode(404);
        response.setStatus("error");
        response.setMessage("Data not found.");
        response.setPayload(0);

        // Assert
        assertEquals(404, response.getCode());
        assertEquals("error", response.getStatus());
        assertEquals("Data not found.", response.getMessage());
        assertEquals(0, response.getPayload());
    }

    @Test
    void constructor_ShouldSetNullPayload() {
        // Arrange
        int code = 200;
        String status = "success";
        String message = "Data retrieved successfully.";

        // Act
        CustomResponse<String> response = new CustomResponse<>(code, message, status, null);

        // Assert
        assertNull(response.getPayload());
    }
}
