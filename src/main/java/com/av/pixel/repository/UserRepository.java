package com.av.pixel.repository;

import com.av.pixel.dao.User;
import com.av.pixel.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    @Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
    User findByEmail(@Param("email") String email);
}
