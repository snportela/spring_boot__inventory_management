package vo.project.inventory.dtos.user;

import java.util.UUID;

public record UserDto (
        UUID userId,

        String name,

        String email
) {
}
