package com.daejangjangi.backend.product.repository;

import com.daejangjangi.backend.product.domain.doc.ProductLookUpLog;
import com.daejangjangi.backend.product.domain.dto.ProductLookUpLogDto;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductLogRepository extends MongoRepository<ProductLookUpLog, String> {

  @Aggregation(pipeline = {
      "{ $match: {lookedAt: { $gte: {$date: ?0}, $lte: {$date: ?1}}, logType: 'VIEW'}}",
      "{ $group: {_id: '$productId', count: {$sum: 1}}}",
      "{ $project: {productId: '$_id', count:  1, _id:  0}}",
      "{ $sort: {count: -1}}",
      "{ $limit: 6}"
  })
  List<ProductLookUpLogDto> findByLookedAtBetween(Date from, Date to);
}
