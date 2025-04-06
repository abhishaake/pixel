package com.av.pixel.repository;

import com.av.pixel.dao.User;
import com.av.pixel.dao.UserToken;
import com.av.pixel.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends BaseRepository<UserToken, Long> {
    UserToken findByAccessTokenAndExpiredFalseAndDeletedFalse(String accessToken);

    @Query(value = "select u.* from user_token ut left join users u on u.code = ut.user_code " +
            "where ut.access_token = :accessToken and ut.expired = false and ut.deleted = false limit 1", nativeQuery = true)
    User getUserFromToken(@Param("accessToken") String accessToken);
}
