package com.av.pixel.cache;

import com.av.pixel.dto.ModelPricingDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class ModelPricingCache {

    @Getter
    @Setter
    public static ConcurrentHashMap<String, ModelPricingDTO> modelPricingMap = new ConcurrentHashMap<>();

}
