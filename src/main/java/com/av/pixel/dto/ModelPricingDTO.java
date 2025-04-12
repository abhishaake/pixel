package com.av.pixel.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ModelPricingDTO {

    String model;

    Double basePrice;
    Double basePriceMultiplier;

    Double turboRenderPrice;
    Double turboRenderPriceMultiplier;

    Double qualityRenderPrice;
    Double qualityRenderPriceMultiplier;

    Double privacyCost;
    Double privacyCostMultiplier;

    Double seedCost;
    Double seedCostMultiplier;

    Double negativePromptCost;
    Double negativePromptCostMultiplier;


    public Integer getFinalBaseCost() {
        return (int) (basePrice * basePriceMultiplier);
    }

    public Integer getFinalTurboRenderCost() {
        return (int) (basePrice * basePriceMultiplier);
    }

    public Integer getFinalQualityRenderCost() {
        return (int) (basePrice * basePriceMultiplier);
    }

    public Integer getFinalPrivacyCost() {
        return (int) (basePrice * basePriceMultiplier);
    }

    public Integer getFinalSeedCost() {
        return (int) (basePrice * basePriceMultiplier);
    }

    public Integer getFinalNegativePromptCost() {
        return (int) (basePrice * basePriceMultiplier);
    }

    public Integer getFinalCost (Integer noOfImages, String render, boolean isPrivate, boolean isSeed, boolean isNegativePrompt) {
        Integer finalCost = noOfImages * getFinalBaseCost();

        if ("QUALITY".equalsIgnoreCase(render)) {
            finalCost += getFinalQualityRenderCost();
        } else {
            finalCost += getFinalTurboRenderCost();
        }
        if (isPrivate) {
            finalCost += getFinalPrivacyCost();
        }
        if (isSeed) {
            finalCost += getFinalSeedCost();
        }
        if (isNegativePrompt) {
            finalCost += getFinalNegativePromptCost();
        }
        return finalCost;
    }

}
