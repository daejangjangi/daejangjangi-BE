package com.daejangjangi.backend.stoolanalysis.repository;

import com.daejangjangi.backend.stoolanalysis.domain.entity.StoolImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoolImageRepository extends JpaRepository<StoolImage, Long> {

}
