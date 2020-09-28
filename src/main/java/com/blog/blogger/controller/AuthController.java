package com.blog.blogger.controller;

import com.blog.blogger.controller.dto.RegisterRequest;
import com.blog.blogger.service.AuthService;
import lombok.AllArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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

    
    
}
