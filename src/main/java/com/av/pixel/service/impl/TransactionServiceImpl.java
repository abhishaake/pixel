package com.av.pixel.service.impl;

import com.av.pixel.dao.Transactions;
import com.av.pixel.enums.OrderStatusEnum;
import com.av.pixel.enums.OrderTypeEnum;
import com.av.pixel.repository.TransactionRepository;
import com.av.pixel.service.TransactionService;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;

    @Override
    public Transactions saveTransaction(String userCode, Double creditsBefore, Double creditsInvolved, Double creditsAfter,
                                        OrderTypeEnum orderType, String orderId, String packageId, OrderStatusEnum status,
                                        String amountInRs, String source, String remarks) {
        Transactions transactions = new Transactions()
                .setUserCode(userCode)
                .setCreditsBefore(creditsBefore)
                .setCreditsInvolved(creditsInvolved)
                .setCreditsAfter(creditsAfter)
                .setOrderType(orderType)
                .setOrderId(orderId)
                .setPackageId(packageId)
                .setStatus(status)
                .setAmountInRs(amountInRs)
                .setSource(source)
                .setRemarks(remarks);

        return transactionRepository.save(transactions);
    }

    @Override
    public Transactions saveTransaction(Transactions transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transactions createOrUpdateTransaction (String userCode, Double creditsBefore, Double creditsInvolved, Double creditsAfter,
                                                   OrderTypeEnum orderType, String orderId, String packageId, OrderStatusEnum status,
                                                   String amountInRs, String source, String remarks) {

        List<Transactions> transactions = transactionRepository.findAllByUserCodeAndOrderIdAndDeletedFalse(userCode, orderId);
        if (CollectionUtils.isEmpty(transactions)) {
            return saveTransaction(userCode, creditsBefore, creditsInvolved, creditsAfter, orderType, orderId, packageId, status, amountInRs, source, remarks);
        }
        Transactions transaction = transactions.get(0);

        if (Objects.nonNull(creditsBefore)) {
            transaction.setCreditsBefore(creditsBefore);
        }
        if (Objects.nonNull(creditsInvolved)) {
            transaction.setCreditsInvolved(creditsInvolved);
        }
        if (Objects.nonNull(creditsAfter)) {
            transaction.setCreditsAfter(creditsAfter);
        }
        if (Objects.nonNull(orderType)) {
            transaction.setOrderType(orderType);
        }
        if (Objects.nonNull(status)) {
            transaction.setStatus(status);
        }
        if (StringUtils.isNotEmpty(orderId)) {
            transaction.setOrderId(orderId);
        }
        if (StringUtils.isNotEmpty(packageId)) {
            transaction.setPackageId(packageId);
        }
        if (StringUtils.isNotEmpty(amountInRs)) {
            transaction.setAmountInRs(amountInRs);
        }
        if (StringUtils.isNotEmpty(source)) {
            transaction.setSource(source);
        }
        if (StringUtils.isNotEmpty(remarks)) {
            transaction.setRemarks(remarks);
        }
        return transactionRepository.save(transaction);
    }

    public Transactions getTransactionByOrderId (String orderId) {
        return transactionRepository.findByOrderIdAndDeletedFalse(orderId);
    }
}
