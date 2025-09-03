package vo.project.inventory.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import vo.project.inventory.domain.enums.UserRole;

public record RegisterDto(

        @NotNull(message = "User Name is required")
        @Size(min = 2, max = 100, message = "User name must be between 2 and 100 characters long")
        String name,

        @NotNull(message = "User E-mail is required")
        @Email(message = "Requester Email must be in a valid email format")
        String email,

        @NotNull(message = "User Password is required")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{10,}$", message = "Password must contain at least one uppercase and lowercase character, a number and a special character")
        String password,

        @NotNull(message = "User Role is required")
        UserRole role
) {
}
