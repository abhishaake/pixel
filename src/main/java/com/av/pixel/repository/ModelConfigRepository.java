package com.av.pixel.repository;

import com.av.pixel.dao.ModelConfig;
import com.av.pixel.repository.base.BaseRepository;

import java.util.List;

public interface ModelConfigRepository extends BaseRepository<ModelConfig, String> {
    List<ModelConfig> findAllByDeletedFalse();
}
