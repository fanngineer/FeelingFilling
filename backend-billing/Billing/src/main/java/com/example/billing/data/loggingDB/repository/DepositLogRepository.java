package com.example.billing.data.loggingDB.repository;

import com.example.billing.data.loggingDB.document.DepositLogDocument;
import com.example.billing.data.loggingDB.document.KakaoPayApproveLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DepositLogRepository extends MongoRepository<DepositLogDocument, String> {
    public List<DepositLogDocument> findByServiceNameAndServiceUserId(String serviceName, int serviceUserId);

    public List<DepositLogDocument> findByCreatedDateBetween(Date from, Date to);
}
