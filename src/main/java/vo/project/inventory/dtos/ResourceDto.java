package vo.project.inventory.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import vo.project.inventory.domain.enums.RepairState;
import vo.project.inventory.domain.enums.Status;
import vo.project.inventory.domain.enums.UseTime;

import java.math.BigDecimal;
import java.util.UUID;

public record ResourceDto(

        UUID resourceId,

        @NotNull(message = "Resource area is required")
        AreaDto area,

        @NotNull(message = "Resource category is required")
        CategoryDto category,

        ReceiptDto receipt,

        BigDecimal price,

        @NotNull(message = "Resource name is required")
        @Size(min = 2, max = 100, message = "Resource name must be between 2 and 100 characters long")
        String name,

        @NotBlank(message = "Resource description is required")
        String description,

        @Pattern(regexp = "^(?:\\d{4}|[Nn]/[Aa])$", message = "Input must be a valid year format or 'N/A'.")
        String manufactureYear,

        String serialNumber,

        @NotNull(message = "Resource repair state is required")
        RepairState repairState,

        String resourceNumber,

        @NotNull(message = "Resource status is required")
        Status status,

        String observation,

        @NotNull(message = "Resource use time is required")
        UseTime useTime
) {
}
