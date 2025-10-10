package vo.project.inventory.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import vo.project.inventory.domain.enums.LoanStatus;

import java.time.Instant;
import java.util.UUID;

public record LoanDto(

        UUID loanId,

        @NotNull(message = "Student Name is required")
        @Size(min = 2, max = 100, message = "Student Name must be between 2 and 100 characters long")
        String studentName,

        @NotNull(message = "Student Id is required")
        String studentId,

        @NotNull(message = "Loan Date is required")
        Instant loanDate,

        @NotNull(message = "Due Date is required")
        Instant dueDate,

        @NotNull(message = "Loan Status is required")
        LoanStatus loanStatus,

        String observation
) {
}
