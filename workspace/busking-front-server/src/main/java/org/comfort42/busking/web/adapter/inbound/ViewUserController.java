package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.port.inbound.UserViewUseCase;
import org.comfort42.busking.common.WebAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ViewUserController {

    private final UserViewUseCase userViewUseCase;

    @GetMapping()
    public ResponseEntity<?> ViewUser(){

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> DetailUser(@PathVariable int userId){

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
