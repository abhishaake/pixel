package com.av.pixel.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class ModelConfigDTO {

    String model;

    Map<String, Object> config;
}
