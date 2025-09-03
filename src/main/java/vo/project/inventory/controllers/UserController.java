package vo.project.inventory.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.project.inventory.dtos.user.UserDto;
import vo.project.inventory.dtos.user.UserRedeemPasswordDto;
import vo.project.inventory.dtos.user.UserResetPasswordDto;
import vo.project.inventory.services.UserService;
import vo.project.inventory.specifications.UserSpec;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listUsers(
            @RequestParam(required = false) String name,
            Pageable pageable
    ) {
        Map<String, Object> response = userService.findAll(UserSpec.nameContains(name), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") UUID userId) {
        UserDto foundUser = userService.findOne(userId);
        return ResponseEntity.status(HttpStatus.OK).body(foundUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("id") UUID userId,
            @Valid @RequestBody UserDto userDto
            ) {
        UserDto updatedUser = userService.update(userId, userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") UUID userId) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
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

