package com.dogbreed.models.entities;

import java.util.List;
import java.util.Map;

public class DogApiResponse {
    private String status;
    private Map<String, List<String>> message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, List<String>> getMessage() {
        return message;
    }

    public void setMessage(Map<String, List<String>> message) {
        this.message = message;
    }
}
