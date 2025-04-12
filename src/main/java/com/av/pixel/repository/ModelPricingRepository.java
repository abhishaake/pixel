package com.av.pixel.repository;

import com.av.pixel.dao.ModelPricing;
import com.av.pixel.repository.base.BaseRepository;

import java.util.List;

public interface ModelPricingRepository extends BaseRepository<ModelPricing, String> {

    List<ModelPricing> findAllByDeletedFalse();
}
