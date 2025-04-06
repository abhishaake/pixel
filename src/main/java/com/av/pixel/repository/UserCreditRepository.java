package com.av.pixel.repository;

import com.av.pixel.dao.UserCredit;
import com.av.pixel.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCreditRepository extends BaseRepository<UserCredit, Long> {

    @Query(value = "select * from user_credit where user_code = :userCode and deleted = false", nativeQuery = true)
    Optional<UserCredit> findByUserCodeAndDeletedFalse(String userCode);
}
