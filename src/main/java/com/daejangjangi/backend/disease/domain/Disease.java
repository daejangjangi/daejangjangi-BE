package com.daejangjangi.backend.disease.domain;

import com.daejangjangi.backend.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Table(name = "diseases")
@Getter
@Entity
public class Disease extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "disease_id")
  private Long id;

  @Column(name = "disease_name", length = 50, nullable = false, unique = true)
  private String name;
}
