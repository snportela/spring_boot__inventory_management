package vo.project.inventory.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record LabDto(
        UUID labId,

        @NotBlank(message = "Lab name is required")
        @Size(min = 2, max = 100, message = "Lab name must be between 2 and 100 characters long")
        String name
) {
}
