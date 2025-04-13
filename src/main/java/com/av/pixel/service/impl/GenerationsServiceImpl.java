package com.av.pixel.service.impl;

import com.av.pixel.cache.RLock;
import com.av.pixel.cache.ModelPricingCache;
import com.av.pixel.client.IdeogramClient;
import com.av.pixel.dao.Generations;
import com.av.pixel.dao.ModelConfig;
import com.av.pixel.dao.User;
import com.av.pixel.dto.GenerationsDTO;
import com.av.pixel.dto.ModelPricingDTO;
import com.av.pixel.dto.UserCreditDTO;
import com.av.pixel.dto.UserDTO;
import com.av.pixel.enums.IdeogramModelEnum;
import com.av.pixel.enums.ImageActionEnum;
import com.av.pixel.enums.ImagePrivacyEnum;
import com.av.pixel.enums.ImageRenderOptionEnum;
import com.av.pixel.enums.PixelModelEnum;
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
import com.av.pixel.request.ImageActionRequest;
import com.av.pixel.request.ImagePricingRequest;
import com.av.pixel.request.ideogram.ImageRequest;
import com.av.pixel.response.GenerationsFilterResponse;
import com.av.pixel.response.ImagePricingResponse;
import com.av.pixel.response.ModelConfigResponse;
import com.av.pixel.response.ideogram.ImageResponse;
import com.av.pixel.service.GenerationsService;
import com.av.pixel.service.LikeGenerationService;
import com.av.pixel.service.UserCreditService;
import com.av.pixel.service.UserService;
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
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

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

    LikeGenerationService likeGenerationService;

    RLock locker;

    UserService userService;


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

        ImageRequest imageRequest = ImageMap.validateAndGetImageRequest(generateRequest);
        List<ImageResponse> imageResponses = generateImage(imageRequest);

        if (Objects.isNull(imageResponses)) {
            throw new Error("some error occurred, please try again");
        }

        userCreditService.updateUserCredit(userDTO.getCode(), Double.valueOf(imageGenerationCost));

        Generations generations = generationHelper.saveUserGeneration(userDTO.getCode(), generateRequest, imageRequest, imageResponses, imageGenerationCost);

        return GenerationsMap.toGenerationsDTO(generations);
    }

    private List<ImageResponse> generateImage (ImageRequest imageRequest) {
        try {
            return ideogramClient.generateImages(imageRequest);
        } catch (Exception e) {
            log.error("[CRITICAL] generate Image error : {}, for req {} ", e.getMessage(), imageRequest, e);
            return null;
        }
    }

    @Override
    public GenerationsFilterResponse filterImages (UserDTO userDTO, GenerationsFilterRequest generationsFilterRequest) {

        Validator.validateFilterImageRequest(generationsFilterRequest, "");

        ImagePrivacyEnum privacyEnum = ImagePrivacyEnum.getEnumByName(generationsFilterRequest.getPrivacy());

        Page<Generations> generationsPage = findByFilters(generationsFilterRequest.getUserCodes(),
                generationsFilterRequest.getCategories(),
                generationsFilterRequest.getStyles(),
                privacyEnum.isPrivateImage(),
                PageRequest.of(generationsFilterRequest.getPage(), generationsFilterRequest.getSize()));

        long totalCount = generationsPage.getTotalElements();
        TreeSet<String> likedGenerations = null;
        if (Objects.nonNull(userDTO) && StringUtils.isNotEmpty(userDTO.getCode())) {
            List<String> genIds = generationsPage.getContent().stream().map(g -> g.getId().toString()).toList();
            likedGenerations = likeGenerationService.getLikedGenerationsByUserCode(userDTO.getCode(), genIds);
        }
        List<String> userCodes = generationsPage.getContent().stream().map(Generations::getUserCode).toList();
        Map<String, User> userMap = userService.getUserCodeVsUserMap(userCodes);

        return new GenerationsFilterResponse(GenerationsMap.toList(generationsPage.getContent(), likedGenerations, userMap),
                totalCount, generationsFilterRequest.getPage(), generationsPage.getNumberOfElements());
    }


    public Page<Generations> findByFilters (List<String> userCodes,
                                            List<String> categories,
                                            List<String> styles,
                                            Boolean privacy,
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

        if (privacy != null) {
            criteriaList.add(Criteria.where("privateImage").is(privacy));
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
                .setPrivateImage(generateRequest.getPrivateImage())
                .setNegativePrompt(StringUtils.isNotEmpty(generateRequest.getNegativePrompt()))
                .setRenderOption(generateRequest.getRenderOption());

        ImagePricingResponse imagePricingResponse = getPricing(imagePricingRequest);
        return imagePricingResponse.getFinalCost();
    }


    @Override
    public ImagePricingResponse getPricing (ImagePricingRequest imagePricingRequest) {

        Validator.validateModelPricingRequest(imagePricingRequest);
        ImageRenderOptionEnum renderOptionEnum = ImageRenderOptionEnum.getEnumByName(imagePricingRequest.getRenderOption());
        IdeogramModelEnum pixelModelEnum = PixelModelEnum.getIdeogramModelByNameAndRenderOption(imagePricingRequest.getModel(), renderOptionEnum);

        if (Objects.isNull(pixelModelEnum)) {
            throw new Error("Please select valid model");
        }

        String model = pixelModelEnum.name();

        ModelPricingDTO modelPricingDTO = ModelPricingCache.getModelPricingMap().get(model);

        if (Objects.isNull(modelPricingDTO)) {
            throw new Error("modelPricing not found");
        }

        boolean isSeed = Objects.nonNull(imagePricingRequest.getSeed());

        Integer finalCost = modelPricingDTO.getFinalCost(imagePricingRequest.getNoOfImages(),
                imagePricingRequest.isPrivateImage(), isSeed, imagePricingRequest.isNegativePrompt());

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

    @Override
    public String performAction (UserDTO userDTO, ImageActionRequest imageActionRequest) {
        String key = "action_" + imageActionRequest.getGenerationId();
        boolean locked = locker.tryLock(key, 1000);

        if (!locked) {
            return "success";
        }
        String res = "success";
        try {
            if (ImageActionEnum.LIKE.equals(imageActionRequest.getAction())) {
                res = likeGenerationService.likeGeneration(userDTO.getCode(), imageActionRequest.getGenerationId());
            } else if (ImageActionEnum.DISLIKE.equals(imageActionRequest.getAction())) {
                res = likeGenerationService.disLikeGeneration(userDTO.getCode(), imageActionRequest.getGenerationId());
            }
            return "success";
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        } finally {
            locker.unlock(key);
        }
        return res;
    }
}
