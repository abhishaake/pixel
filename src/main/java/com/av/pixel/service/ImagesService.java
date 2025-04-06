package com.av.pixel.service;

import com.av.pixel.request.ImageFilterRequest;
import com.av.pixel.response.ImageFilterResponse;

public interface ImagesService {

    ImageFilterResponse filterImages(ImageFilterRequest imageFilterRequest);
}
