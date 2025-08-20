package vo.project.inventory.dtos;

import java.util.UUID;

public record LoanItemResponseDto(

        UUID loanId,

        UUID resourceId,

        String studentName,

        String studentId,

        String resourceName
) {
}
