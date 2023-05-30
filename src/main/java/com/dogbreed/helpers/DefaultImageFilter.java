package com.dogbreed.helpers;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
public class DefaultImageFilter implements ImageFilter {

    @Override
    public List<String> filterImages(List<String> imageUrls) {
        // Implement your default image filtering logic here
        return imageUrls;
    }
}
