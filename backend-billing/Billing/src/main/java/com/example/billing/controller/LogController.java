package com.example.billing.controller;

import com.example.billing.data.dto.ServiceUserDTO;
import com.example.billing.data.dto.TimePeriodDTO;
import com.example.billing.data.loggingDB.document.DepositLogDocument;
import com.example.billing.data.loggingDB.document.KakaoPayApproveLogDocument;
import com.example.billing.data.loggingDB.document.WithdrawalLogDocument;
import com.example.billing.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController("/logs")
public class LogController {

    private final LoggingService loggingService;
    @PostMapping("/user/subscription")
    public ResponseEntity<Map<String,Object>> getSubscriptionLogsByUser(ServiceUserDTO serviceUserDTO){
        List<KakaoPayApproveLogDocument> logs = loggingService.findSubscriptionLogsByUser(serviceUserDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("log", logs);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/user/deposit")
    public ResponseEntity<Map<String,Object>> getDepositLogsByUser(ServiceUserDTO serviceUserDTO){
        List<DepositLogDocument> logs = loggingService.findDepositLogsByUser(serviceUserDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("log", logs);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/user/withdrawal")
    public ResponseEntity<Map<String,Object>> getWithdrawalLogsByUser(ServiceUserDTO serviceUserDTO){
        List<WithdrawalLogDocument> logs = loggingService.findWithdrawalLogsByUser(serviceUserDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("log", logs);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

//    @PostMapping("/period/subscription")
//    public ResponseEntity<Map<String,Object>> getSubscriptionLogsByPeriod(TimePeriodDTO timePeriodDTO){
//
//    }
}
