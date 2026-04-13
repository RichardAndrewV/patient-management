package com.pm.patientservice1.controller;

import com.pm.patientservice1.dto.LoginRequest;
import com.pm.patientservice1.jwt.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

//    @PostMapping("/login")
//    public String login(@RequestBody String username) {
//        return jwtUtil.generateToken(username);
//    }
@PostMapping("/login")
public String login(@RequestBody LoginRequest request) {
    return jwtUtil.generateToken(request.username, request.role);
}
}
