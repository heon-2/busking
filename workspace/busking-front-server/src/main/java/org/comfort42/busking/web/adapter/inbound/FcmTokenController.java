package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fcms")
@RequiredArgsConstructor
public class FcmTokenController {

    @PostMapping
    ResponseEntity<?> saveToken(){

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/send")
    ResponseEntity<?> sendMessage(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
