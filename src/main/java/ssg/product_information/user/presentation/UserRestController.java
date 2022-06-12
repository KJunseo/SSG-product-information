package ssg.product_information.user.presentation;

import java.net.URI;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ssg.product_information.user.application.UserService;
import ssg.product_information.user.application.dto.request.UserCreateRequestDto;
import ssg.product_information.user.presentation.dto.UserAssembler;
import ssg.product_information.user.presentation.dto.request.UserCreateRequest;

@RequestMapping("/users")
@RestController
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid UserCreateRequest request) {
        UserCreateRequestDto requestDto = UserAssembler.userCreateRequestDto(request);
        Long id = userService.create(requestDto);
        URI location = URI.create("/users/" + id);
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.withdrawal(id);
        return ResponseEntity.ok().build();
    }
}
