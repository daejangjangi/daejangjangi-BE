package com.daejangjangi.backend.cardnews.repository;

import com.daejangjangi.backend.cardnews.domain.entity.Cardnews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardnewsRepository extends JpaRepository<Cardnews, Long> {

}
