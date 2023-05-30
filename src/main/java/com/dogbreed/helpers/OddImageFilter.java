package com.dogbreed.helpers;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OddImageFilter implements ImageFilter {

    @Override
    public List<String> filterImages(List<String> imageUrls) {
        // Filter only odd number of images
        List<String> filteredImages = new ArrayList<>();
        for (int i = 0; i < imageUrls.size(); i++) {
            if (i % 2 == 0) {
                filteredImages.add(imageUrls.get(i));
            }
        }
        return filteredImages;
    }
}
