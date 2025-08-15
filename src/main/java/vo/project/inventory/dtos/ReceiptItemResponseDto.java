package vo.project.inventory.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record ReceiptItemResponseDto(

        UUID receiptId,

        UUID resourceId,

        String receiptNumber,

        String resourceName,

        String resourceDescription,

        BigDecimal unitPrice,

        String observation
) {


}
