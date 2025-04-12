package com.av.pixel.service.impl;

import com.av.pixel.cache.ModelPricingCache;
import com.av.pixel.client.IdeogramClient;
import com.av.pixel.dao.Generations;
import com.av.pixel.dao.ModelConfig;
import com.av.pixel.dto.GenerationsDTO;
import com.av.pixel.dto.ModelPricingDTO;
import com.av.pixel.dto.UserCreditDTO;
import com.av.pixel.dto.UserDTO;
import com.av.pixel.exception.Error;
import com.av.pixel.helper.GenerationHelper;
import com.av.pixel.helper.Validator;
import com.av.pixel.mapper.GenerationsMap;
import com.av.pixel.mapper.ModelConfigMap;
import com.av.pixel.mapper.UserCreditMap;
import com.av.pixel.mapper.ideogram.ImageMap;
import com.av.pixel.repository.GenerationsRepository;
import com.av.pixel.repository.ModelConfigRepository;
import com.av.pixel.request.GenerateRequest;
import com.av.pixel.request.GenerationsFilterRequest;
import com.av.pixel.request.ImagePricingRequest;
import com.av.pixel.request.ideogram.ImageRequest;
import com.av.pixel.response.GenerationsFilterResponse;
import com.av.pixel.response.ImagePricingResponse;
import com.av.pixel.response.ModelConfigResponse;
import com.av.pixel.response.ideogram.ImageResponse;
import com.av.pixel.service.GenerationsService;
import com.av.pixel.service.UserCreditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class GenerationsServiceImpl implements GenerationsService {

    GenerationsRepository generationsRepository;
    MongoTemplate mongoTemplate;

    UserCreditService userCreditService;
    ModelConfigRepository modelConfigRepository;

    IdeogramClient ideogramClient;

    GenerationHelper generationHelper;

    @Override
    public GenerationsDTO generate (UserDTO userDTO, GenerateRequest generateRequest) {
        Validator.validateGenerateRequest(generateRequest);

        UserCreditDTO userCreditDTO = userCreditService.getUserCredit(userDTO.getCode());

        if (Objects.isNull(userCreditDTO)) {
            userCreditDTO = UserCreditMap.userCreditDTO(userCreditService.createNewUserCredit(userDTO.getCode()));
        }

        Double availableCredits = userCreditDTO.getAvailable();
        Integer imageGenerationCost = getCost(generateRequest);

        if (availableCredits < imageGenerationCost) {
            throw new Error("Not enough credits");
        }

        List<ImageResponse> imageResponses = generateImage(ImageMap.toImageRequest(generateRequest));

        if (Objects.isNull(imageResponses)) {
            throw new Error("some error occurred, please try again");
        }

        userCreditService.updateUserCredit(userDTO.getCode(), Double.valueOf(imageGenerationCost));

        GenerationsDTO generationsDTO = GenerationsMap.toGenerationsDTO(userDTO.getCode(), generateRequest.getModel(), generateRequest.getPrompt(), imageResponses);

        generationHelper.saveUserGeneration(generationsDTO, imageGenerationCost);

        return generationsDTO;
    }

    private List<ImageResponse> generateImage (ImageRequest imageRequest) {
        try {
            return ideogramClient.generateImages(imageRequest);
        }
        catch (Exception e) {
            log.error("[CRITICAL] generate Image error : {}, for req {} ", e.getMessage(), imageRequest, e);
            return null;
        }
    }

    @Override
    public GenerationsFilterResponse filterImages (GenerationsFilterRequest generationsFilterRequest) {

        Validator.validateFilterImageRequest(generationsFilterRequest, "");

        Page<Generations> generationsPage = findByFilters(generationsFilterRequest.getUserCodes(),
                generationsFilterRequest.getCategories(),
                generationsFilterRequest.getStyles(),
                generationsFilterRequest.getPrivacy(),
                PageRequest.of(generationsFilterRequest.getPage(), generationsFilterRequest.getSize()));

        long totalCount = generationsPage.getTotalElements();

        return new GenerationsFilterResponse(GenerationsMap.toList(generationsPage.getContent()),
                totalCount, generationsFilterRequest.getPage(), generationsPage.getNumberOfElements());
    }

    public Page<Generations> findByFilters(List<String> userCodes,
                                           List<String> categories,
                                           List<String> styles,
                                           String privacy,
                                           Pageable pageable) {

        List<Criteria> criteriaList = new ArrayList<>();

        if (userCodes != null && !userCodes.isEmpty()) {
            criteriaList.add(Criteria.where("userCode").in(userCodes));
        }

        if (categories != null && !categories.isEmpty()) {
            criteriaList.add(Criteria.where("category").in(categories));
        }

        if (styles != null && !styles.isEmpty()) {
            criteriaList.add(Criteria.where("style").in(styles));
        }

        if (privacy != null && !privacy.isBlank()) {
            criteriaList.add(Criteria.where("privacy").is(privacy));
        }

        Query query = new Query();

        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        long total = mongoTemplate.count(query, Generations.class);

        query.with(pageable);

        List<Generations> results = mongoTemplate.find(query, Generations.class);

        return new PageImpl<>(results, pageable, total);
    }

    private Integer getCost (GenerateRequest generateRequest) {
        ImagePricingRequest imagePricingRequest = new ImagePricingRequest().setModel(generateRequest.getModel())
                .setNoOfImages(generateRequest.getNumberOfImages())
                .setSeed(generateRequest.getSeed())
                .setPrivate(generateRequest.getPrivateImage())
                .setHasNegativePrompt(StringUtils.isNotEmpty(generateRequest.getNegativePrompt()))
                .setRenderOption(generateRequest.getRenderOption());

        ImagePricingResponse imagePricingResponse = getPricing(imagePricingRequest);
        return imagePricingResponse.getFinalCost();
    }


    @Override
    public ImagePricingResponse getPricing (ImagePricingRequest imagePricingRequest) {

        Validator.validateModelPricingRequest(imagePricingRequest);

        String model = imagePricingRequest.getModel();

        ModelPricingDTO modelPricingDTO = ModelPricingCache.getModelPricingMap().get(model);

        if (Objects.isNull(modelPricingDTO)) {
            throw new Error("modelPricing not found");
        }

        boolean isSeed = Objects.nonNull(imagePricingRequest.getSeed());

        Integer finalCost = modelPricingDTO.getFinalCost(imagePricingRequest.getNoOfImages(), imagePricingRequest.getRenderOption(),
                imagePricingRequest.isPrivate(), isSeed, imagePricingRequest.isHasNegativePrompt());

        return new ImagePricingResponse()
                .setFinalCost(finalCost);
    }

    @Override
    public ModelConfigResponse getModelConfigs () {
        List<ModelConfig> modelConfigs = modelConfigRepository.findAllByDeletedFalse();

        if (CollectionUtils.isEmpty(modelConfigs)) {
            throw new Error("no model config found");
        }

        return new ModelConfigResponse()
                .setModels(ModelConfigMap.toList(modelConfigs));
    }
}
