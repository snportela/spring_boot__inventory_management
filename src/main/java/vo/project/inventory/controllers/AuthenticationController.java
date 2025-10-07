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

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto registerDto) {
        userService.register(registerDto);
        return ResponseEntity.status(HttpStatus.OK).body("User registered successfully.");
    }

    @PostMapping("/redeem-password")
    public ResponseEntity<String> redeemPassword(@RequestBody UserRedeemPasswordDto userDto) {
        userService.redeemPassword(userDto.email());
        return ResponseEntity.status(HttpStatus.OK).body("Sent the redeem password link to your email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody UserResetPasswordDto userDto) {
        userService.resetPassword(userDto.token(), userDto.password());

        return ResponseEntity.status(HttpStatus.OK).body("Credentials updated.");
    }
}
