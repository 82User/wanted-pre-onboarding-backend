package wanted.preonboarding.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.preonboarding.entity.User;
import wanted.preonboarding.jwt.TokenInfo;
import wanted.preonboarding.service.UserServiceImpl;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @PostMapping("/singup")
    public ResponseEntity<User> signup(@Valid @RequestBody User user){
        User createUser = userServiceImpl.signup(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(@RequestBody User user){
        TokenInfo tokenInfo = userServiceImpl.login(user);

        return ResponseEntity.status(HttpStatus.OK).body(tokenInfo);
    }
}
