package com.daejangjangi.backend.daejangtoon.repository;

import com.daejangjangi.backend.daejangtoon.domain.entity.Daejangtoon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DaejangtoonRepository extends JpaRepository<Daejangtoon, Long> {

  List<Daejangtoon> findAllByOrderByIdDesc();
}
