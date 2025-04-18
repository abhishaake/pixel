package com.av.pixel.service;

import com.av.pixel.dao.Transactions;

public interface TransactionService {

    Transactions saveTransaction(String userCode, Double amountInRs, Double credits, String txnType, String source, String refId, String orderType, String packageId);
}
