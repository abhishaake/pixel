package com.av.pixel.repository;

import com.av.pixel.dao.Audit;
import com.av.pixel.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends BaseRepository<Audit, String> {
}
