package vo.project.inventory.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import vo.project.inventory.domain.Receipt;
import vo.project.inventory.domain.ReceiptItem;
import vo.project.inventory.domain.Resource;
import vo.project.inventory.dtos.ReceiptItemCreateDto;
import vo.project.inventory.dtos.ReceiptItemResponseDto;
import vo.project.inventory.dtos.ReceiptItemUpdateDto;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReceiptItemMapper {

    ReceiptItem toEntity(ReceiptItemCreateDto createDTO);

    ReceiptItemResponseDto toResponseDto(ReceiptItem receiptItem);

    @Mapping(source = "receiptItem.receiptId", target = "receiptId")
    @Mapping(source = "receiptItem.resourceId", target = "resourceId")
    @Mapping(source = "receiptItem.unitPrice", target = "unitPrice")
    @Mapping(source = "receiptItem.observation", target = "observation")
    @Mapping(source = "receipt.receiptNumber", target = "receiptNumber")
    @Mapping(source = "resource.name", target = "resourceName")
    @Mapping(source = "resource.description", target = "resourceDescription")
    ReceiptItemResponseDto toResponseDto(
            ReceiptItem receiptItem,
            Receipt receipt,
            Resource resource
    );

    @Mapping(target = "receiptId", ignore = true)
    @Mapping(target = "resourceId", ignore = true)
    void updateEntityFromDTO(ReceiptItemUpdateDto updateDTO, @MappingTarget ReceiptItem receiptItem);

    default ReceiptItemResponseDto mapQueryResult(Object[] result) {
        if (result == null || result.length != 3) {
            return null;
        }

        ReceiptItem receiptItem = (ReceiptItem) result[0];
        Receipt receipt = (Receipt) result[1];
        Resource resource = (Resource) result[2];

        return toResponseDto(receiptItem, receipt, resource);
    }

    default List<ReceiptItemResponseDto> mapQueryResultList(List<Object[]> results) {
        return results.stream()
                .map(this::mapQueryResult)
                .collect(Collectors.toList());
    }
}


