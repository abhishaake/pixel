package com.av.pixel.response;

import com.av.pixel.enums.PurchaseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PaymentVerificationResponse {
    PurchaseStatusEnum status;
    String orderId;
    Long purchaseTime;
    String exceptionMsg;
    Double userCredits;

    public PaymentVerificationResponse(PurchaseStatusEnum status, String orderId, Long purchaseTime) {
        this.status = status;
        this.orderId = orderId;
        this.purchaseTime = purchaseTime;
    }

    public boolean isSuccess () {
        return PurchaseStatusEnum.SUCCESS.equals(status);
    }
}
