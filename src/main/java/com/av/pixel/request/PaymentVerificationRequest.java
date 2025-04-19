package com.av.pixel.request;

import lombok.Data;

@Data
public class PaymentVerificationRequest {
    String userCode;
    String productId;
    String purchaseToken;
}
