package vo.project.inventory.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vo.project.inventory.domain.Receipt;
import vo.project.inventory.dtos.ReceiptDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReceiptMapper {

    ReceiptDto receiptToDto(Receipt receipt);

    Receipt dtoToReceipt(ReceiptDto receiptDto);
}
