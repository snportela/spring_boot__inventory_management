package vo.project.inventory.dtos;

import java.math.BigDecimal;

public record ReceiptItemUpdateDto(

        BigDecimal unitPrice,

        String observation
) {
}
