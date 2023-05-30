package com.dogbreed.dto;

import java.util.List;

public class DogDetailsDto {

    private List<String> dogImages;

    /**
     * @return List<Object> return the dogImages
     */
    public List<String> getDogImages() {
        return dogImages;
    }

    /**
     * @param dogImages the dogImages to set
     */
    public void setDogImages(List<String> dogImages) {
        this.dogImages = dogImages;
    }

}
