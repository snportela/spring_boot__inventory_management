package vo.project.inventory.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.project.inventory.dtos.user.*;
import vo.project.inventory.security.UserDetailsImpl;
import vo.project.inventory.security.services.AuthTokenService;
import vo.project.inventory.services.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AuthTokenService authTokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, AuthTokenService authTokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.authTokenService = authTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid AuthenticationDto authDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = authTokenService.generateToken((UserDetailsImpl) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDto(token));
    }

    @PostMapping("/redeem-password")
    public ResponseEntity<Object> redeemPassword(@RequestBody UserRedeemPasswordDto userDto) {
        userService.redeemPassword(userDto.email());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Sent the redeem password link to user email");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody UserResetPasswordDto userDto) {
        userService.resetPassword(userDto.token(), userDto.password());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password updated.");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
