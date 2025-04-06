package com.av.pixel.repository;

import com.av.pixel.dao.Images;
import com.av.pixel.dto.ImagesDTO;
import com.av.pixel.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImagesRepository extends BaseRepository<Images, Long> {

    @Query("SELECT img FROM Images img " +
            "WHERE (:userCodes IS NULL OR img.userCode IN :userCodes) " +
            "AND (:categories IS NULL OR img.category IN :categories) " +
            "AND (:styles IS NULL OR img.style IN :styles) " +
            "AND (:privacy IS NULL OR img.privacy = :privacy)")
    Page<Images> filterImages(@Param("userCodes") List<String> userCodes,
                              @Param("categories") List<String> categories,
                              @Param("styles") List<String> styles,
                              @Param("privacy") String privacy,
                              Pageable pageable);
}
