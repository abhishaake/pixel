package com.av.pixel.repository.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    List<T> findByDeletedFalse();

    T findByIdAndDeletedFalse(ID id);

    List<T> findByIdInAndDeletedFalse(List<ID> id);
}
