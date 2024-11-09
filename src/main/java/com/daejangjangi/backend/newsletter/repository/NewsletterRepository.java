package com.daejangjangi.backend.newsletter.repository;

import com.daejangjangi.backend.newsletter.domain.entity.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {

}
