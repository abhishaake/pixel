package com.av.pixel.helper;

import com.av.pixel.dao.GenerationHistory;
import com.av.pixel.dao.Generations;
import com.av.pixel.dto.GenerationsDTO;
import com.av.pixel.mapper.GenerationsMap;
import com.av.pixel.repository.GenerationHistoryRepository;
import com.av.pixel.repository.GenerationsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
@AllArgsConstructor
public class GenerationHelper {

    GenerationsRepository generationsRepository;

    GenerationHistoryRepository generationHistoryRepository;

    @Async
    public void saveUserGeneration (GenerationsDTO generationsDTO, Integer imageGenerationCost) {
        Generations generations = GenerationsMap.toEntity(generationsDTO);

        if (Objects.isNull(generations)) {
            return;
        }

        generations = generationsRepository.save(generations);
        String id = generations.getId().toString();

        GenerationHistory generationHistory = new GenerationHistory()
                .setGenerationId(id)
                .setUserCode(generations.getUserCode())
                .setCost(Double.valueOf(imageGenerationCost));

        generationHistoryRepository.save(generationHistory);
    }
}
