package com.av.pixel.repository;

import com.av.pixel.dao.Transactions;
import com.av.pixel.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends BaseRepository<Transactions, String> {
}
