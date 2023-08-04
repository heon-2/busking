package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.comfort42.busking.application.port.inbound.ViewUserPayload;
import org.comfort42.busking.application.port.inbound.ViewUserUseCase;
import org.comfort42.busking.common.WebAdapter;
import org.comfort42.busking.web.security.TokenAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.comfort42.busking.application.domain.model.User;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ViewUserController {

    private final ViewUserUseCase viewUserUseCase;

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
