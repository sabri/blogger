package com.blog.blogger.service;

import com.blog.blogger.controller.dto.AuthenticationResponse;
import com.blog.blogger.controller.dto.RefreshTokenRequest;
import com.blog.blogger.controller.dto.RegisterRequest;
import com.blog.blogger.controller.dto.RequestLogin;
import com.blog.blogger.exceptions.SpringException;
import com.blog.blogger.modul.NotificationEmail;
import com.blog.blogger.modul.User;
import com.blog.blogger.modul.VerificationToken;
import com.blog.blogger.repository.UserRepository;
import com.blog.blogger.repository.VerificationTokenRepository;
import com.blog.blogger.security.JwtProvider;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AuthService {

private final PasswordEncoder passwordEncoder;
private final UserRepository userRepository;
private final  VerificationTokenRepository verificationTokenRepository;
private final MailService mailService;
private final AuthenticationManager authenticationManager;
private final JwtProvider jwtProvider;
private RefreshTokenRequest refreshTokenRequest;
    private final RefreshTokenService refreshTokenService;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, MailService mailService, AuthenticationManager authenticationManager, JwtProvider jwtProvider, RefreshTokenService refreshTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
    }


    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
        String token = generateVerificationToken(user);
    mailService.sendMail(new NotificationEmail("Please Activate your Account",
            user.getEmail(), "Thank you for signing up to Spring Reddit, " +
            "please click on the below url to activate your account : " +
            "http://localhost:8080/api/auth/accountVerification/" + token));
}

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;

    }

    @Transactional
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public void verifyToken(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());

    }
    @Transactional
   public void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
       User user = userRepository.findByUsername(username).orElseThrow(()->new SpringException("not found user"));
       user.setEnabled(true);
       userRepository.save(user);

    }

    public   AuthenticationResponse loginJWT(RequestLogin login) {
   Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(token, loginJWT.getUsername());
    }
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
