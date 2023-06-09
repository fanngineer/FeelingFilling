package com.a702.feelingfilling.domain.user.controller;

import com.a702.feelingfilling.domain.user.model.dto.UserDTO;
import com.a702.feelingfilling.domain.user.model.dto.UserJoinDTO;
import com.a702.feelingfilling.domain.user.model.dto.UserKakaoRequestDTO;
import com.a702.feelingfilling.domain.user.model.dto.UserKakaoResponseDTO;
import com.a702.feelingfilling.domain.user.service.UserService;
import com.a702.feelingfilling.global.jwt.JwtTokenService;
import com.a702.feelingfilling.global.jwt.JwtTokens;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user")
@Api(tags = {"회원 관리 API"})
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";
    private static final String ALREADY_EXIST = "ALREADY EXIST";

    private final JwtTokenService jwtTokenService;
    @ApiOperation(value = "카카오 로그인", notes = "카카오 로그인 API", response = Map.class)
    @PostMapping("/kakao")
    public ResponseEntity<?> kakaoLogin(@ApiParam("로그인하고자하는 카카오 oauth아이디")@RequestBody UserKakaoRequestDTO userKakaoDTO) {
        log.info("카카오 로그인 : " + userKakaoDTO);
        UserKakaoResponseDTO responseDTO = userService.kakaoLogin(userKakaoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    //2. 회원가입
    @ApiOperation(value = "회원 가입", notes = "회원 가입 API", response = Map.class)
    @PostMapping
    public ResponseEntity<?> join(@ApiParam("회원가입하고자하는 user 정보")@RequestBody UserJoinDTO userJoinDTO) {
        log.info("회원가입 요청 : " + userJoinDTO);
        Map<String, Object> resultMap;
        try {
            resultMap = new HashMap<>();
            JwtTokens tokens = userService.join(userJoinDTO);
            resultMap.put("message", SUCCESS);
            resultMap.put("access-token", tokens.getAccessToken());
            resultMap.put("refresh-token", tokens.getRefreshToken());
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            resultMap = new HashMap<>();
            resultMap.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(resultMap);
        }
    }//join

    //3. 정보조회
    @GetMapping()
//	@PreAuthorize("hasRole('ROLE_USER')" )
    public ResponseEntity<?> getUser() {
        log.info("회원 정보 조회 요청");
        Map<String, Object> resultMap;
        try {
            resultMap = new HashMap<>();
			      resultMap.put("user", userService.getUser());
            resultMap.put("message", SUCCESS);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            resultMap = new HashMap<>();
            resultMap.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(resultMap);
        }
    }

    //4. 정보수정
    @PutMapping
//	@PreAuthorize("hasRole('ROLE_AMDIN') or hasRole('ROLE_USER')" )
    public ResponseEntity<?> modifyUser(@RequestBody UserDTO userDTO) {
        log.info("회원 정보 수정 요청");
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("user", userService.modifyUser(userDTO));
            resultMap.put("message", SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(resultMap);
        }
    }

    //5. 회원 탈퇴
    @DeleteMapping()
//    @PreAuthorize(("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')"))
    public ResponseEntity<?> deleteUser(){
        log.info("회원 탈퇴 요청");
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int userId = userService.deleteUser();
            resultMap.put("message", SUCCESS);
            log.debug("탈퇴한 회원 : {}", userId);
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            log.error("회원 탈퇴 중 에러 발생 : {}",e);
            resultMap.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(resultMap);
        }
    }
    //6. 뱃지 조회
    @GetMapping("/badge")
//	@PreAuthorize(("hasRole('ROLE_ADMIN')") or ("hasRole('ROLE_USER')"))
    public ResponseEntity<?> getUserBadge() {
        log.info("회원 뱃지 조회 요청");
        HttpStatus status = HttpStatus.OK;
        Map<String, Object> resultMap = new HashMap<>();

        try {
            
            List<Integer> badges = userService.getUserBadge();
            resultMap.put("badges", badges);
            log.debug("회원 뱃지 조회 ,", badges);

            resultMap.put("message", SUCCESS);
        } catch (Exception e) {
            log.error("회원 뱃지 조회 에러 : {}", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(resultMap, status);
    }
    // 7. 관리자용 회원 목록 조회
    @GetMapping("/list")
//	@PreAuthorize("hasRole('ROLE_AMDIN')" )
    public ResponseEntity<?> getUserListForAdmin() {
        log.info("관리자의 회원 리스트 조회 요청");
        Map<String, Object> resultMap;
        try {
            resultMap = new HashMap<>();
            resultMap.put("users", userService.getUserListForAdmin());
            resultMap.put("message", SUCCESS);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            resultMap = new HashMap<>();
            resultMap.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(resultMap);
        }
    }
    
    // 8. 관리자용 회원 정보 조회
    @GetMapping("/{userId}")
//	@PreAuthorize("hasRole('ROLE_AMDIN')" )
    public ResponseEntity<?> getUserForAdmin(@PathVariable int userId) {
        log.info("관리자의 회원 정보 조회 요청");
        Map<String, Object> resultMap;
        try {
            resultMap = new HashMap<>();
            resultMap.put("user", userService.getUserForAdmin(userId));
            resultMap.put("message", SUCCESS);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            resultMap = new HashMap<>();
            resultMap.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(resultMap);
        }
    }
    
    // 9. 관리자용 회원 탈퇴
    @DeleteMapping("/{userId}")
//    @PreAuthorize(("hasRole('ROLE_ADMIN')"))
    public ResponseEntity<?> deleteUserForAdmin(@ApiParam("탈퇴 시킬 아이디") @PathVariable Integer userId){
        log.info("관리자가 회원 탈퇴 요청");
        Map<String, Object> resultMap = new HashMap<>();
        try {
            userService.deleteUserForAdmin(userId);
            resultMap.put("message", SUCCESS);
            log.debug("관리자가 탈퇴시킨 회원 : {}", userId);
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            log.error("관리자의 회원 탈퇴 처리 중 에러 발생 : {}",e);
            resultMap.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(resultMap);
        }
    }

    //10. 결제 등록
    @GetMapping("/register")
    public ResponseEntity<?> registerBill(){
        log.info("결제 등록 요청");
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String url = userService.registerBill();
            resultMap.put("url", url);
            resultMap.put("message", SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            log.error("결제 등록 에러 발생 : {}",e);
            resultMap.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(resultMap);
        }
    }
}
