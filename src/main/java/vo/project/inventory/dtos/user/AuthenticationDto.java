package vo.project.inventory.dtos.user;

import jakarta.validation.constraints.NotNull;

public record AuthenticationDto(

        @NotNull(message = "User Email is required")
        String email,

        @NotNull(message = "User Password is required")
        String password
) {
}
