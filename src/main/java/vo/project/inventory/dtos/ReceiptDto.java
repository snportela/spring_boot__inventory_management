package vo.project.inventory.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ReceiptDto(

        UUID receiptId,

        @NotNull(message = "Receipt Number is required")
        @Size(min = 2, max = 100, message = "Receipt Number must be between 2 and 100 characters long")
        String receiptNumber,

        String accessKey,

        @NotNull(message = "Receipt Price is required")
        BigDecimal price,

        @NotNull(message = "Receipt Supplier is required")
        @Size(min = 2, max = 100, message = "Receipt Supplier must be between 2 and 100 characters long")
        String supplier,

        @NotNull(message = "Receipt Date is required")
        Instant receiptDate
) {
}
