package com.av.pixel.service.impl;

import com.av.pixel.dao.Transactions;
import com.av.pixel.repository.TransactionRepository;
import com.av.pixel.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;

    @Override
    public Transactions saveTransaction(String userCode, Double amount, String txnType, String source, String refId, String orderType) {
        Transactions transactions = new Transactions()
                .setUserCode(userCode)
                .setAmount(amount)
                .setTxnType(txnType)
                .setSource(source)
                .setRefId(refId)
                .setOrderType(orderType);

        return transactionRepository.save(transactions);
    }
}
