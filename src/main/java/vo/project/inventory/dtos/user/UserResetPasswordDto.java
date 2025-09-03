package vo.project.inventory.dtos.user;

public record UserResetPasswordDto(
        String token,
        String password
) {
}
