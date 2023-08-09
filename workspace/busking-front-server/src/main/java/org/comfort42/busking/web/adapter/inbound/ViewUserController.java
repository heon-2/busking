package org.comfort42.busking.web.adapter.inbound;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.comfort42.busking.application.port.inbound.ViewUserPayload;
import org.comfort42.busking.application.port.inbound.ViewUserUseCase;
import org.comfort42.busking.common.WebAdapter;
import org.comfort42.busking.web.security.TokenAuthentication;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.User;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.comfort42.busking.application.domain.model.User;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@WebAdapter
@RestController
@RequiredArgsConstructor

@RequestMapping("/api/users")

public class ViewUserController {

    private final ViewUserUseCase viewUserUseCase;




    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "목록 조회 성공", content = @Content(schema = @Schema(implementation = ViewUserPayload.class))),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근" ),
            @ApiResponse(responseCode = "401", description = "로그인 인증 문제" )
    })
    @Operation(summary = "유저 목록",description = "관리자의 companyId와 같은 모든 유저를 불러온다. 20명씩 불러온다.")

    @GetMapping("/list/{page}")
    public ResponseEntity<?> ViewUser(Authentication authentication, @PathVariable long page) {
        try {

            if (authentication == null || !(authentication instanceof TokenAuthentication)) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).build();
            }else{

                long companyId = Integer.parseInt(((TokenAuthentication) authentication).getCompanyId());
                return new ResponseEntity<ConcurrentHashMap<String,Object>>(viewUserUseCase.userList(companyId,page)
                                                                                                    ,HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상세보기 조회 성공", content = @Content(schema = @Schema(implementation = ViewUserPayload.class))),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근" ),
            @ApiResponse(responseCode = "401", description = "로그인 인증 문제" )
    })
    @Operation(summary = "상세보기", description = "로그인한 유저의 정보를 본다.")

    @GetMapping()
    public ResponseEntity<?> DetailUser(Authentication authentication) {

        try {
            if (authentication == null || !(authentication instanceof TokenAuthentication)) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).build();
            }else{
                return new ResponseEntity<ViewUserPayload>(viewUserUseCase
                        .userDetail(UUID.fromString(authentication.getName())),HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<ViewUserPayload>(HttpStatus.BAD_REQUEST);
        }
    }
}
