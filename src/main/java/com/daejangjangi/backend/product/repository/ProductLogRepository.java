package com.daejangjangi.backend.product.repository;

import com.daejangjangi.backend.product.domain.doc.ProductLookUpLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductLogRepository extends MongoRepository<ProductLookUpLog, String> {

}
