package com.av.pixel.controller;

import com.av.pixel.response.base.Response;
import com.av.pixel.scheduler.CacheScheduler;
import com.av.pixel.service.ModelPricingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/utility")
@AllArgsConstructor
public class UtilityController {

    CacheScheduler cacheScheduler;

    @GetMapping("/health")
    public Response<String> health() {
        return new Response<>(HttpStatus.OK, "healthy");
    }

    @PostMapping("/load-pricing-cache")
    public Response<String> loadPricingCache() {
        cacheScheduler.loadModelPricing();
        return new Response<>(HttpStatus.OK, "success");
    }
}
