package com.av.pixel.repository;

import com.av.pixel.dao.User;
import com.av.pixel.dao.UserCredit;
import com.av.pixel.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCreditRepository extends BaseRepository<UserCredit, Long> {

    UserCredit findByUserAndDeletedFalse(User user);
}
