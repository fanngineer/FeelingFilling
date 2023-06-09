package com.a702.feelingfilling.domain.history.controller;

import com.a702.feelingfilling.domain.history.model.dto.LogDTO;
import com.a702.feelingfilling.domain.history.service.LogService;
import com.a702.feelingfilling.domain.user.model.dto.UserLoginDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/log")
@Api(tags = {"거래내역 API"})
@Slf4j
public class LogController {
	private static final String SUCCESS = "SUCCESS";
	private static final String FAIL = "FAIL";
	
	@Autowired
	private LogService logService;
	
		
	@ApiOperation(value = "월별 거래내역 조회", notes = "월별 거래내역 조회 API", response = Map.class)
	@GetMapping("/{year}/{month}")
	public ResponseEntity<?> getUserMonthLog(@ApiParam("년도")  @PathVariable int year
			,@ApiParam("월")  @PathVariable int month){
		log.info("월별 거래내역 조회 ");
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> resultMap = new HashMap<>();
		
		try{
			//이번 달 저금
			int userId = ((UserLoginDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
			log.info("회원번호 : "+userId + " /"+year+"년 "+month+"월");

			List<LogDTO> logs = logService.getUserMonthLog(userId,year,month);

			resultMap.put("logs",logs);
			log.debug("% 회원의 %d월 거래내역 : ", userId, month, logs);
			
			resultMap.put("message", SUCCESS);
			
		}
		catch (Exception e){
			status = HttpStatus.BAD_REQUEST;
			resultMap.put("message", FAIL);
			log.error("회원 월별 거래내역 조회 에러 : {} ",e);
		}
		
		return new ResponseEntity<>(resultMap,status);
	}
	
}
