package com.av.pixel.repository;

import com.av.pixel.dao.Generations;
import com.av.pixel.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenerationsRepository extends BaseRepository<Generations, String> {
}
