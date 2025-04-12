package com.av.pixel.repository;

import com.av.pixel.dao.User;
import com.av.pixel.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends BaseRepository<User, String> {

    User findByEmailAndDeletedFalse(String email);

    User findByCodeAndDeletedFalse(String code);
}
