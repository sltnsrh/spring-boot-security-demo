package com.example.demospringsecurity.controller;

import com.example.demospringsecurity.model.User;
import com.example.demospringsecurity.model.dto.AuthRequestDto;
import com.example.demospringsecurity.model.dto.RegistrationRequestDto;
import com.example.demospringsecurity.security.jwt.JwtTokenProvider;
import com.example.demospringsecurity.service.UserService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> login(@RequestBody AuthRequestDto requestDto) {
        try {
            String userName = requestDto.getUserName();
            String password = requestDto.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
            User user = userService.getUserByUserName(userName);
            if (user == null) {
                throw new UsernameNotFoundException("Can't found user with user name " + userName);
            }
            String token = jwtTokenProvider.createToken(userName, user.getRoles());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", userName);
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegistrationRequestDto requestDto) {
        User user = userService.register(requestDto);
        return ResponseEntity.ok().body(user);
    }
}
