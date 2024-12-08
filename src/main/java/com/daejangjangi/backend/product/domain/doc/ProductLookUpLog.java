package com.daejangjangi.backend.product.domain.doc;

import com.daejangjangi.backend.product.domain.enums.LogType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "productlookuplogs")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductLookUpLog {

  @Id
  private String id;
  private Long memberId;
  private Long productId;
  private LocalDateTime lookedAt;
  private LogType logType;

  @Builder
  public ProductLookUpLog(
      Long memberId,
      Long productId
  ) {
    this.id = UUID.randomUUID().toString();
    this.memberId = memberId;
    this.productId = productId;
    this.lookedAt = LocalDateTime.now();
    this.logType = LogType.VIEW;
  }
}
