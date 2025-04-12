package com.av.pixel.helper;

import com.av.pixel.exception.Error;
import com.av.pixel.repository.UserRepository;
import com.av.pixel.request.GenerateRequest;
import com.av.pixel.request.GenerationsFilterRequest;
import com.av.pixel.request.ImagePricingRequest;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

@Component
@Slf4j
@AllArgsConstructor
public class Validator {

    UserRepository userRepository;

    public static void validateNonEmpty(String str, String error) {
        if (StringUtils.isEmpty(str)) {
            throw new Error(error);
        }
    }

    public static void validateNonNull(Object obj, String error) {
        if (Objects.isNull(obj)) {
            throw new Error(error);
        }
    }

    public static void validateNonNull(Integer obj, String error) {
        if (Objects.isNull(obj)) {
            throw new Error(error);
        }
    }

    public static void validateFilterImageRequest(GenerationsFilterRequest filterRequest, String error) {
        if (Objects.isNull(filterRequest)) {
            throw new Error(error);
        }
        if (CollectionUtils.isEmpty(filterRequest.getUserCodes()) && CollectionUtils.isEmpty(filterRequest.getCategories())
            && CollectionUtils.isEmpty(filterRequest.getStyles())) {
            throw new Error(error);
        }
        if (Objects.isNull(filterRequest.getPage())){
            filterRequest.setPage(0);
        }
        if (Objects.isNull(filterRequest.getStyles())){
            filterRequest.setSize(50);
        }
    }

    public static void validatePhone(String phone, String error) {

    }

    public static void validateGenerateRequest (GenerateRequest generateRequest) {
        validateNonNull(generateRequest, "");
        validateNonEmpty(generateRequest.getPrompt(), "");
        validateNonNull(generateRequest.getNumberOfImages(), "");
    }

    private static void validateNoOfImageRange (Integer noOfImages, String error) {
        if ((noOfImages < 1) || (noOfImages > 5)) {
            throw new Error(error);
        }
    }

    private static void validateSeedRange (Long seed, String error) {
        if (Objects.nonNull(seed) && ((seed < 1) || (seed > 1000000))){
            throw new Error(error);
        }
    }

    public static void validateModelPricingRequest (ImagePricingRequest imagePricingRequest) {
        validateNonNull(imagePricingRequest, "");
        validateNonEmpty(imagePricingRequest.getModel(), "");
        validateNonNull(imagePricingRequest.getNoOfImages(), "");
        validateNoOfImageRange(imagePricingRequest.getNoOfImages(), "");
        validateSeedRange(imagePricingRequest.getSeed(), "");
    }
}
