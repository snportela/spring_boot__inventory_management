package vo.project.inventory.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LoanItemCreateDto(

        @NotNull(message = "Loan ID is required")
        UUID loanId,

        @NotNull(message = "Resource ID is required")
        UUID resourceId
) {
}
