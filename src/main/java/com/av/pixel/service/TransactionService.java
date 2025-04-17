package com.av.pixel.service;

import com.av.pixel.dao.Transactions;

public interface TransactionService {

    Transactions saveTransaction(String userCode, Double amount, String txnType, String source, String refId, String orderType);
}
