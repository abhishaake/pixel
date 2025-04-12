package com.av.pixel.service;

import com.av.pixel.dto.GenerationsDTO;
import com.av.pixel.dto.UserDTO;
import com.av.pixel.request.GenerateRequest;
import com.av.pixel.request.GenerationsFilterRequest;
import com.av.pixel.request.ImagePricingRequest;
import com.av.pixel.response.GenerationsFilterResponse;
import com.av.pixel.response.ImagePricingResponse;
import com.av.pixel.response.ModelConfigResponse;

public interface GenerationsService {

    GenerationsFilterResponse filterImages(GenerationsFilterRequest imageFilterRequest);

    GenerationsDTO generate(UserDTO userDTO, GenerateRequest generateRequest);

    ImagePricingResponse getPricing (ImagePricingRequest imagePricingRequest);

    ModelConfigResponse getModelConfigs ();
}
