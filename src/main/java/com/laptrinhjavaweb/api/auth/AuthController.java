package com.laptrinhjavaweb.api.auth;

import com.laptrinhjavaweb.config.JwtTokenProvider;
import com.laptrinhjavaweb.dto.LoginRequestDTO;
import com.laptrinhjavaweb.dto.LoginResponseDTO;
import com.laptrinhjavaweb.dto.MyUserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for JWT authentication")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    @Operation(
            summary = "Login and get JWT token",
            description = "Authenticates a user and returns a JWT token for secured API calls."
    )
    @SecurityRequirements
    @ApiResponse(responseCode = "200", description = "Login success")
    @ApiResponse(responseCode = "401", description = "Invalid username or password")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(loginRequest.getUsername());

        MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();

        LoginResponseDTO response = new LoginResponseDTO(
                token,
                userDetail.getId(),
                userDetail.getUsername(),
                userDetail.getFullName());

        return ResponseEntity.ok(response);
    }
}
