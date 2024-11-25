package com.daejangjangi.backend.stoollog.repository;

import com.daejangjangi.backend.stoollog.domain.entity.Stoollog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoollogRepository extends JpaRepository<Stoollog, Long> {

}
