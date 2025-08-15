package vo.project.inventory.dtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ReceiptItemCreateDto(

        @NotNull(message = "Receipt ID is required")
        UUID receiptId,

        @NotNull(message = "Resource ID is required")
        UUID resourceId,

        @NotNull(message = "Resource Unit Price is required")
        BigDecimal unitPrice,

        String observation
) {
}
