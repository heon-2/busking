package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.inbound.ViewUserUseCase;
import org.comfort42.busking.application.port.inbound.ViewUserCommand;
import org.comfort42.busking.common.WebAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ViewUserController {

    private final ViewUserUseCase viewUserUseCase;

    @GetMapping()
    public ResponseEntity<?> ViewUser() {
        try {

            return new ResponseEntity<List<ViewUserCommand>>(viewUserUseCase.UserList(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> DetailUser(@PathVariable int userId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
