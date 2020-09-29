package com.blog.blogger.controller;

import com.blog.blogger.controller.dto.RegisterRequest;
import com.blog.blogger.controller.dto.RequestLogin;
import com.blog.blogger.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/api/auth")
public class AuthController {
private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<String> signup (@RequestBody RegisterRequest registerRequest){
authService.signup(registerRequest);
return new ResponseEntity<>("user Registration successful", HttpStatus.OK);
    }

    @GetMapping(value = "accountVerification/{token}")
    public ResponseEntity<String> verfication(@PathVariable String token){
        authService.verifyToken(token);
        return new ResponseEntity<>("account is verified ", HttpStatus.OK);

    }
    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody RequestLogin login){
        authService.login(login);
        return new ResponseEntity<>("you are welcome", HttpStatus.OK);
    }

}
