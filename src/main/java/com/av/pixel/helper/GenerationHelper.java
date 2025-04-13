package com.av.pixel.helper;

import com.av.pixel.dao.GenerationHistory;
import com.av.pixel.dao.Generations;
import com.av.pixel.dto.GenerationsDTO;
import com.av.pixel.enums.ImageStyleEnum;
import com.av.pixel.mapper.GenerationsMap;
import com.av.pixel.repository.GenerationHistoryRepository;
import com.av.pixel.repository.GenerationsRepository;
import com.av.pixel.request.GenerateRequest;
import com.av.pixel.request.ideogram.ImageRequest;
import com.av.pixel.response.ideogram.ImageResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@AllArgsConstructor
public class GenerationHelper {

    GenerationsRepository generationsRepository;

    GenerationHistoryRepository generationHistoryRepository;

    MongoTemplate mongoTemplate;

    public Generations saveUserGeneration (String userCode, GenerateRequest generateRequest, ImageRequest imageRequest, List<ImageResponse> imageResponses, Integer imageGenerationCost) {
        Generations generations = GenerationsMap.toGenerationsEntity(userCode, generateRequest.getModel(), generateRequest.getPrompt(),
                generateRequest.getRenderOption(), generateRequest.getPrivateImage(), imageResponses);

        if (Objects.isNull(imageRequest.getStyleType())) {
            generations.setStyle(ImageStyleEnum.AUTO.name());
        }

        generations = generationsRepository.save(generations);
        String id = generations.getId().toString();

        GenerationHistory generationHistory = new GenerationHistory()
                .setGenerationId(id)
                .setUserCode(generations.getUserCode())
                .setCost(Double.valueOf(imageGenerationCost));

        generationHistoryRepository.save(generationHistory);

        return generations;
    }

    public Generations getById (String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            Query query = new Query(Criteria.where("_id").is(objectId));
            return mongoTemplate.findOne(query, Generations.class);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
