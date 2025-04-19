package com.av.pixel.service;

import com.av.pixel.dao.Transactions;
import com.av.pixel.enums.OrderStatusEnum;
import com.av.pixel.enums.OrderTypeEnum;

public interface TransactionService {

    Transactions saveTransaction(String userCode, Double creditsBefore, Double creditsInvolved, Double creditsAfter,
                                 OrderTypeEnum orderType, String orderId, String packageId, OrderStatusEnum status,
                                 String amountInRs, String source, String remarks);

    Transactions saveTransaction(Transactions transaction);

    Transactions createOrUpdateTransaction (String userCode, Double creditsBefore, Double creditsInvolved, Double creditsAfter,
                                            OrderTypeEnum orderType, String orderId, String packageId, OrderStatusEnum status,
                                            String amountInRs, String source, String remarks);

    Transactions getTransactionByOrderId (String orderId);
}
