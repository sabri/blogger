package com.blog.blogger.controller;

import com.blog.blogger.controller.dto.AuthenticationResponse;
import com.blog.blogger.controller.dto.RefreshTokenRequest;
import com.blog.blogger.controller.dto.RegisterRequest;
import com.blog.blogger.controller.dto.RequestLogin;
import com.blog.blogger.service.AuthService;
import com.blog.blogger.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.OK;
import javax.validation.Valid;


@RestController
@RequestMapping(value="/api/auth")
public class AuthController {
private final AuthService authService;
private  final RefreshTokenService refreshTokenService;
    public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<String> signup (@RequestBody RegisterRequest registerRequest){
authService.signup(registerRequest);
return new ResponseEntity<>("user Registration successful", HttpStatus.OK);
    }

    @GetMapping(value = "accountVerification/{token}")
    public ResponseEntity<String> verification(@PathVariable String token){
        authService.verifyToken(token);
        return new ResponseEntity<>("account is verified ", HttpStatus.OK);

    }
    @PostMapping(value = "/login")
    public AuthenticationResponse login(@RequestBody RequestLogin login){
       return authService.loginJWT(login);
    }
    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }
}
